package com.example.shopsphere.ui.navigation.model

sealed class Screen(val route: String) {

    // Screen Routes
    data object Splash : Screen("splash")
    data object Welcome : Screen("welcome")
    data object SignUp : Screen("signup")
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Categories : Screen("categories")
    data object Coupon : Screen("coupon")
    data object Favourite : Screen("favourite")
    data object Profile : Screen("profile")
    data object OurProduct : Screen("myproduct")
    data object Checkout : Screen("checkout/{userLocationId}") {
        fun createRoute(userLocationId: Int?) : String = "checkout/$userLocationId"
    }
    data object Payment : Screen("payment")
    data object SuccessPayment : Screen("successpayment")
    data object Address : Screen("address")
    data object YourOrder : Screen("yourorder")
    data object AddAddress : Screen("addaddress")
    data object MyCart : Screen("mycart")
    data object Notification : Screen("notification")
    data object DetailProduct : Screen("home/{productId}") {
        fun createRoute(productId: Int) = "home/$productId"
    }

    // Graph Routes
    data object AuthNav : Screen("AUTH_NAV_GRAPH")
    data object MainNav : Screen("MAIN_NAV_GRAPH")
    data object Root : Screen("ROOT")

}
