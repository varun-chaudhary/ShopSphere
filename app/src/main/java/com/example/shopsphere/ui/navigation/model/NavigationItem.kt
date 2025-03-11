package com.example.shopsphere.ui.navigation.model

import androidx.compose.ui.graphics.painter.Painter

data class NavigationItem(
    val title: String,
    val icon: Painter,
    val iconActive: Painter,
    val screen: Screen
)