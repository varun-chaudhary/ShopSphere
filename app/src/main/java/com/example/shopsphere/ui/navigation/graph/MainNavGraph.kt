package com.example.shopsphere.ui.navigation.graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shopsphere.ui.navigation.model.Screen
import com.example.shopsphere.ui.screen.address.AddressScreen
import com.example.shopsphere.ui.screen.address.add_address.AddAddressScreen
import com.example.shopsphere.ui.screen.categories.CategoriesScreen
import com.example.shopsphere.ui.screen.checkout.CheckoutScreen
import com.example.shopsphere.ui.screen.comparison.CompareVendorsScreen
import com.example.shopsphere.ui.screen.coupon.CouponScreen
import com.example.shopsphere.ui.screen.detail.DetailScreen
import com.example.shopsphere.ui.screen.favourite.FavouriteScreen
import com.example.shopsphere.ui.screen.home.HomeScreen
import com.example.shopsphere.ui.screen.mycart.MyCartScreen
import com.example.shopsphere.ui.screen.notification.NotificationScreen
import com.example.shopsphere.ui.screen.ourproduct.OurProductScreen
import com.example.shopsphere.ui.screen.payment.PaymentScreen
import com.example.shopsphere.ui.screen.payment.success_payment.SuccessPaymentScreen
import com.example.shopsphere.ui.screen.profile.ProfileScreen
import com.example.shopsphere.ui.screen.yourorder.YourOrderScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(),
) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(paddingValues),
        route = Screen.MainNav.route,
        startDestination = Screen.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(100)) },
        exitTransition = { fadeOut(animationSpec = tween(100)) },
        popEnterTransition = { fadeIn(animationSpec = tween(100)) },
        popExitTransition = { fadeOut(animationSpec = tween(100)) }
    ) {
        authNav(navController)
        composable(
            route = Screen.Home.route,
        ) {
            HomeScreen(navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(
            route = Screen.DetailProduct.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            ),
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("productId") ?: -2
            DetailScreen(
                productId = id,
                navController
            )
        }
        composable(
            route = Screen.Checkout.route,
            arguments = listOf(
                navArgument("userLocationId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("userLocationId") ?: -1
            CheckoutScreen(
                navController = navController,
                selectedLocationId = id
            )
        }
        composable(
            route = Screen.YourOrder.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) {
            YourOrderScreen(navController = navController)
        }
        composable(
            route = Screen.CompareVendors.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            }
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: -1
            CompareVendorsScreen(productId = productId, navController = navController)
        }

        composable(
            route = Screen.SuccessPayment.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            }
        ) {
            SuccessPaymentScreen(navController = navController)
        }
        composable(
            route = Screen.Address.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) {
            AddressScreen(navHostController = navController)
        }
        composable(
            route = Screen.Payment.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) {
            PaymentScreen(navController = navController)
        }
        composable(
            route = Screen.AddAddress.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) {
            AddAddressScreen(navController = navController)
        }
        composable(
            route = Screen.MyCart.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) {
            MyCartScreen(navController)
        }
        composable(
            route = Screen.Notification.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ){
            NotificationScreen(navController = navController)
        }
        composable(Screen.Coupon.route){ CouponScreen() }
        composable(Screen.Favourite.route){ FavouriteScreen(navController) }
        composable(Screen.OurProduct.route){ OurProductScreen(navController) }
        composable(
            route = Screen.Categories.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ){
            CategoriesScreen(navController)
        }
    }
}