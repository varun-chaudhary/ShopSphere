package com.example.shopsphere.ui.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.data.repository.ShopSphereRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val repository: ShopSphereRepository
) : ViewModel() {
    fun getUsername() : LiveData<String> {
        return repository.getUsername().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            withContext(NonCancellable) {
                repository.logout()
            }
        }
    }
}