package com.example.shopsphere.ui.screen.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopsphere.ui.component.BottomNavigation
import com.example.shopsphere.ui.navigation.graph.MainNavGraph
import com.example.shopsphere.ui.navigation.model.Screen

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    val currentDestination = navController
        .currentBackStackEntryAsState()
        .value?.destination?.route

    Scaffold(
        content = { innerPadding ->
            MainNavGraph(
                navController = navController,
                paddingValues = innerPadding
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentDestination !in listOf(
                    Screen.DetailProduct.route,
                    Screen.MyCart.route,
                    Screen.Categories.route,
                    Screen.Welcome.route,
                    Screen.Login.route,
                    Screen.SignUp.route,
                    Screen.Notification.route,
                    Screen.Checkout.route,
                    Screen.Address.route,
                    Screen.AddAddress.route,
                    Screen.Payment.route,
                    Screen.SuccessPayment.route,
                    Screen.YourOrder.route
                )
            ) {
                BottomNavigation(
                    navController = navController,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    )
}