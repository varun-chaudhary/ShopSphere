package com.example.shopsphere.data.local

import androidx.compose.ui.graphics.Color
import com.example.shopsphere.data.local.entity.UserLocation
import com.example.shopsphere.model.Coupon
import com.example.shopsphere.model.PaymentMethod
import com.example.shopsphere.model.Shipping
import com.example.shopsphere.R


object DataDummy {
    val dummyUserLocation = listOf(
        UserLocation(
            id = 1,
            name = "Varun Chaudhary",
            address = "Paras Estate No. 123, " +
                    "Jalandhar " +
                    "India 144021"
        ),
        UserLocation(
            id = 2,
            name = "Lakhan",
            address = "Paras Estate No. 124," +
                    "Jalandhar " +
                    "India 144021"
        ),
    )
    val dummyShipping = listOf(
        Shipping(
            name = "Regular",
            price = 13.00,
            description = "Estimated time of arrival 2 - 3 days"
        ),
        Shipping(
            name = "Premium",
            price = 15.00,
            description = "Estimated time of arrival 1 - 2 days"
        ),

    )
    val dummyPaymentMethod = listOf(
        PaymentMethod(
            icon = R.drawable.icon_master_card,
            name = "Master Card"
        ),
        PaymentMethod(
            icon = R.drawable.icon_paypal,
            name = "Paypal"
        ),
        PaymentMethod(
            icon = R.drawable.icon_visa,
            name = "Visa"
        ),
    )
    val dummyCoupon = listOf(
        Coupon(
            discountedPrice = "FREE DELIVERY",
            description = "Applies to get free Delivery",
            expiredDate = "31 December 2025",
            color1 = Color(0xFF9733EE),
            color2 = Color(0xFFDA22FF),
            couponCode = "ADLRTS4TA"
        ),
        Coupon(
            discountedPrice = "30%",
            description = "Applies to get 30% off",
            expiredDate = "31 December 2025",
            color1 = Color(0xFFFFA726),
            color2 = Color(0xFFFFD54F),
            couponCode = "ADERTS4TA"
        )
    )
}