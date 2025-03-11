package com.example.shopsphere.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shopsphere.data.local.entity.Cart
import com.example.shopsphere.data.repository.ShopSphereRepository

class HomeViewModel(
    private val repository: ShopSphereRepository
) : ViewModel(){
    val cartItems: LiveData<List<Cart>> = repository.getAllCartItems()
    val unreadNotificationCount: LiveData<Int> = repository.getUnReadNotification()
}