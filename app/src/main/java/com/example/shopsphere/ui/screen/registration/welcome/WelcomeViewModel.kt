package com.example.shopsphere.ui.screen.registration.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.data.local.entity.Notification
import com.example.shopsphere.data.repository.ShopSphereRepository
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val repository: ShopSphereRepository
) : ViewModel() {
    fun saveLoginData(username: String, token: String) {
        viewModelScope.launch {
            repository.saveLoginData(username, token)
        }
    }

    fun addNotification(notification: Notification) {
        viewModelScope.launch {
            repository.addNotification(notification)
        }
    }
}