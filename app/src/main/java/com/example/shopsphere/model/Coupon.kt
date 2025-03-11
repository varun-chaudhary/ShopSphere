package com.example.shopsphere.model

import androidx.compose.ui.graphics.Color

data class Coupon(
    val discountedPrice: String,
    val description: String,
    val expiredDate: String,
    val couponCode: String,
    val color1: Color,
    val color2: Color
)