package com.example.shopsphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.shopsphere.ui.theme.ShopSphereTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var keepSplashOnScreen = true

        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOnScreen
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
                )
            )
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            ShopSphereTheme(dynamicColor = false){
                RootNavigationGraph(navController = rememberNavController()) {
                    keepSplashOnScreen = false
                }
            }
        }
    }
}
