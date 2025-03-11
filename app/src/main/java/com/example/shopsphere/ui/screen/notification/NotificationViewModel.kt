package com.example.shopsphere.ui.screen.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.data.repository.ShopSphereRepository
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: ShopSphereRepository
) : ViewModel() {
    val notifications = repository.getAllNotifications()

    fun markAsRead(notificationId: Int) {
        viewModelScope.launch {
            repository.markAsRead(notificationId)
        }
    }
}