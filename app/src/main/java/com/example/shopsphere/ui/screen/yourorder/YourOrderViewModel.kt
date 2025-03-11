package com.example.shopsphere.ui.screen.yourorder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shopsphere.data.local.entity.Checkout
import com.example.shopsphere.data.repository.ShopSphereRepository

class YourOrderViewModel(
    private val repository: ShopSphereRepository
) : ViewModel() {

    val checkoutItems: LiveData<List<Checkout>> = repository.getAllCheckout()


}