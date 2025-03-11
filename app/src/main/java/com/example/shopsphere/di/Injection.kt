package com.example.shopsphere.di

import android.content.Context
import android.location.Geocoder
import com.example.shopsphere.data.local.database.ShopSphereRoomDatabase
import com.example.shopsphere.data.remote.retrofit.ApiConfig
import com.example.shopsphere.data.repository.ShopSphereRepository
import com.example.shopsphere.util.SessionPreferences
import com.example.shopsphere.util.dataStore
import com.google.android.gms.location.LocationServices
import java.util.Locale

object Injection {
    fun provideRepository(context: Context): ShopSphereRepository {
        val user = SessionPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val cartDao = ShopSphereRoomDatabase.getDatabase(context).cartDao()
        val favoriteDao = ShopSphereRoomDatabase.getDatabase(context).favouriteDao()
        val orderDao = ShopSphereRoomDatabase.getDatabase(context).orderDao()
        val checkoutDao = ShopSphereRoomDatabase.getDatabase(context).checkoutDao()
        val notificationDao = ShopSphereRoomDatabase.getDatabase(context).notificationDao()
        val userLocationDao = ShopSphereRoomDatabase.getDatabase(context).userLocationDao()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val geocoder = Geocoder(context, Locale.getDefault())
        return ShopSphereRepository.getInstance(
            apiService,
            user,
            cartDao,
            favoriteDao,
            orderDao,
            checkoutDao,
            notificationDao,
            userLocationDao,
            fusedLocationClient,
            geocoder,
        )
    }
}