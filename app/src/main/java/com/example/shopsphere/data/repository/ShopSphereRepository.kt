package com.example.shopsphere.data.repository

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shopsphere.data.local.dao.CartDao
import com.example.shopsphere.data.local.dao.CheckoutDao
import com.example.shopsphere.data.local.dao.FavouriteDao
import com.example.shopsphere.data.local.dao.NotificationDao
import com.example.shopsphere.data.local.dao.OrderDao
import com.example.shopsphere.data.local.dao.UserLocationDao
import com.example.shopsphere.data.local.entity.Cart
import com.example.shopsphere.data.local.entity.Checkout
import com.example.shopsphere.data.local.entity.Favourite
import com.example.shopsphere.data.local.entity.Notification
import com.example.shopsphere.data.local.entity.Order
import com.example.shopsphere.data.local.entity.UserLocation
import com.example.shopsphere.data.remote.retrofit.ApiService
import com.example.shopsphere.model.LoginResponse
import com.example.shopsphere.model.ProductResponseItem
import com.example.shopsphere.model.UserResponse
import com.example.shopsphere.util.SessionPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class ShopSphereRepository private constructor(
    private val apiService : ApiService,
    private val pref: SessionPreferences,
    private val cartDao: CartDao,
    private val favouriteDao: FavouriteDao,
    private val orderDao: OrderDao,
    private val checkoutDao: CheckoutDao,
    private val notificationDao: NotificationDao,
    private val userLocationDao: UserLocationDao,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val geocoder: Geocoder
){

    // Authentification

    fun getSession() : Flow<LoginResponse> {
        return pref.getSession()
    }

    fun getUsername() : Flow<String> {
        return pref.getUsername()
    }

    suspend fun saveLoginData(username: String, token: String) {
        pref.saveLoginData(username, token)
    }

    suspend fun logout() {
        pref.logout()
        cartDao.deleteAllItems()
        favouriteDao.deleteAllItems()
        orderDao.deleteAllOrders()
        checkoutDao.deleteAllitems()
        notificationDao.deleteAllNotifications()
        userLocationDao.deleteAllItems()
    }

    suspend fun login(
        username: String,
        password: String
    ): LoginResponse {
        return apiService.login(username, password)
    }

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): UserResponse {
        return apiService.register(username, email, password)
    }

    // Notification

    suspend fun addNotification(notification: Notification) {
        return notificationDao.insert(notification)
    }

    fun getAllNotifications(): LiveData<List<Notification>> {
        return notificationDao.getAllNotifications()
    }

    suspend fun markAsRead(notificationId: Int) {
        notificationDao.markAsRead(notificationId)
    }

    fun getUnReadNotification(): LiveData<Int> {
        return notificationDao.getUnreadNotificationCount()
    }

    // Checkout

    suspend fun addCheckout(checkout: Checkout) {
        return checkoutDao.insert(checkout)
    }

    fun getLatestCheckout(): LiveData<Checkout> {
        return checkoutDao.getLatestCheckout()
    }

    fun getAllCheckout() : LiveData<List<Checkout>> {
        return checkoutDao.getAllCheckouts()
    }

    suspend fun updatePaymentMethodCheckout(checkoutId: Int, paymentMethod: String) {
        return checkoutDao.updatePaymentMethod(checkoutId, paymentMethod)
    }

    // User Location

    suspend fun addUsersLocation(userLocation: UserLocation) {
        return userLocationDao.insertUserLocation(userLocation)
    }

    suspend fun deleteUsersLocation(id: Int) {
        return userLocationDao.deleteUserLocation(id)
    }

    fun getAllUsersLocation(): LiveData<List<UserLocation>> {
        return userLocationDao.getAllUserLocations()
    }

    fun getUserLocationById(id: Int): LiveData<UserLocation> {
        return userLocationDao.getUserLocationById(id)
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() : LiveData<LatLng?> {
        val liveData = MutableLiveData<LatLng?>()
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                Log.d("ShopSphereRepository", "Location: ${location.latitude}, ${location.longitude}")
                liveData.value = LatLng(location.latitude, location.longitude)
            } else {
                Log.d("ShopSphereRepository", "No location found")
            }
        }
        return liveData
    }

    suspend fun getAddressFromLatLng(latLng: LatLng) : String {
        return withContext(Dispatchers.IO) {
            val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            val result = address?.firstOrNull()
            result?.getAddressLine(0) ?: "Unkown Location"
        }
    }

    // Order

    fun getLatestOrder(): LiveData<Order> {
        return orderDao.getLatestOrder()
    }

    suspend fun addOrder(order: Order) {
        return orderDao.insert(order)
    }

    // Favourite Product

    fun isProductFavorited(productId: Int): LiveData<Boolean> {
        return favouriteDao.isProductFavorited(productId)
    }

    suspend fun addToFavourite(favourite: Favourite) {
        favouriteDao.insert(favourite)
    }

    fun getAllFavourites(): LiveData<List<Favourite>> {
        return favouriteDao.getAllFavourites()
    }

    suspend fun deleteFavouriteById(id: Int) {
        favouriteDao.deleteById(id)
    }

    // Cart Item

    suspend fun updateCartById(cartId: Int, quantity: Int) {
        cartDao.updateQuantity(cartId, quantity)
    }

    suspend fun deleteCartById(cartId: Int) {
        cartDao.deleteById(cartId)
    }

    fun getAllCartItems() : LiveData<List<Cart>> {
        return cartDao.getAllCart()
    }

    suspend fun addToCart(cart: Cart) {
        val existingCartItem = cartDao.getCartItemByProductId(cart.productId)
        if (existingCartItem != null){
            val newQuantity = existingCartItem.productQuantity + cart.productQuantity
            cartDao.updateQuantity(existingCartItem.id, newQuantity)
        } else {
            cartDao.insert(cart)
        }
    }

    // Product from API

    suspend fun getAllProducts(limit: Int): List<ProductResponseItem> {
        return apiService.getAllProduct(limit)
    }

    suspend fun getProductByCategory(
        category: String,
        limit: Int
    ): List<ProductResponseItem> {
        return apiService.getProductByCategory(category, limit)
    }

    suspend fun getSingleProduct(id: Int): ProductResponseItem {
        return apiService.getSingleProduct(id)
    }

    suspend fun sortProduct(sort: String): List<ProductResponseItem> {
        return apiService.sortProduct(sort)
    }

    suspend fun searchProduct(query: String): List<ProductResponseItem> {
        val allproduct = apiService.getAllProduct(Int.MAX_VALUE)
        return allproduct.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    companion object {
        fun getInstance(
            apiService: ApiService,
            userPreferences: SessionPreferences,
            cartDao: CartDao,
            favouriteDao: FavouriteDao,
            orderDao: OrderDao,
            checkoutDao: CheckoutDao,
            notificationDao: NotificationDao,
            userLocationDao: UserLocationDao,
            fusedLocationClient: FusedLocationProviderClient,
            geocoder: Geocoder
        ) = ShopSphereRepository(
            apiService,
            userPreferences,
            cartDao,
            favouriteDao,
            orderDao,
            checkoutDao,
            notificationDao,
            userLocationDao,
            fusedLocationClient,
            geocoder,
        )
    }
}