package com.example.shopsphere.model

import com.example.shopsphere.R

data class SliderImage(
    val image: Int
)

val dummySliderImage = listOf(
    SliderImage(R.drawable.image_slider_one),
    SliderImage(R.drawable.image_slider_two),
    SliderImage(R.drawable.image_slider_three),
    SliderImage(R.drawable.image_slider_four),
)
