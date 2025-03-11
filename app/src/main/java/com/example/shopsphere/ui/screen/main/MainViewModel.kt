package com.example.shopsphere.ui.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.shopsphere.data.repository.ShopSphereRepository
import com.example.shopsphere.model.LoginResponse

class MainViewModel (
    private val repository: ShopSphereRepository
) : ViewModel() {
    fun getSession() : LiveData<LoginResponse> {
        return repository.getSession().asLiveData()
    }
}