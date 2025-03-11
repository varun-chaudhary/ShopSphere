package com.example.shopsphere.ui.screen.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.data.local.entity.Cart
import com.example.shopsphere.data.local.entity.Checkout
import com.example.shopsphere.data.local.entity.Notification
import com.example.shopsphere.data.local.entity.Order
import com.example.shopsphere.data.local.entity.UserLocation
import com.example.shopsphere.data.repository.ShopSphereRepository
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val repository: ShopSphereRepository
) : ViewModel() {
    val orderItems: LiveData<Order> = repository.getLatestOrder()

    private val _selectedShippingId = MutableLiveData<Int?>()
    val selectedShippingId: LiveData<Int?> get() = _selectedShippingId

    private val _selectedCouponId = MutableLiveData<Int?>()
    val selectedCouponId: LiveData<Int?> get() = _selectedCouponId

    fun selectCoupon(id: Int) {
        _selectedCouponId.value = id
    }

    fun addNotification(
        message: String,
        notificationType: String,
        firstProductImage: String,
        firstProductName: String,
        quantityCheckout: Int,
    ) {
        val notification = Notification(
            message = message,
            notificationType = notificationType,
            firstProductImage = firstProductImage,
            firstProductName = firstProductName,
            quantityCheckout = quantityCheckout
        )

        viewModelScope.launch { repository.addNotification(notification) }
    }

    fun addToCheckout(
        receiverName: String,
        receiverAddress: String,
        orderItems: List<Cart>,
        shippingMethod: String,
        shippingCost: Double,
        shippingDescription: String,
        paymentMethod: String = "",
        coupon: String = "",
        totalPrice: Double
    ) {
        val checkout = Checkout(
            receiverName = receiverName,
            receiverAddress = receiverAddress,
            orderItems = orderItems,
            shippingMethod = shippingMethod,
            shippingCost = shippingCost,
            shippingDescription = shippingDescription,
            paymentMethod = paymentMethod,
            totalPrice = totalPrice,
            coupon = coupon
        )

        viewModelScope.launch {
            repository.addCheckout(checkout)
        }
    }

    fun selectShipping(id: Int) {
        _selectedShippingId.value = id
    }

    fun getUserLocationById(id: Int): LiveData<UserLocation> {
        return repository.getUserLocationById(id)
    }
}