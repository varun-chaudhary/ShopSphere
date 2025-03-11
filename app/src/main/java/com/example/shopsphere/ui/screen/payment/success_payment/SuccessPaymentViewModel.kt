package com.example.shopsphere.ui.screen.payment.success_payment

import androidx.lifecycle.ViewModel
import com.example.shopsphere.data.repository.ShopSphereRepository

class SuccessPaymentViewModel(
    private val repository: ShopSphereRepository
) : ViewModel() {
    val latestCheckout = repository.getLatestCheckout()
}