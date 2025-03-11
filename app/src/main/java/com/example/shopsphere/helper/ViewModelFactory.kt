package com.example.shopsphere.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopsphere.data.repository.ShopSphereRepository
import com.example.shopsphere.di.Injection
import com.example.shopsphere.ui.screen.address.AddressViewModel
import com.example.shopsphere.ui.screen.address.add_address.AddAddressViewModel
import com.example.shopsphere.ui.screen.checkout.CheckoutViewModel
import com.example.shopsphere.ui.screen.detail.DetailViewModel
import com.example.shopsphere.ui.screen.favourite.FavouriteViewModel
import com.example.shopsphere.ui.screen.foryou.ForYouViewModel
import com.example.shopsphere.ui.screen.home.HomeViewModel
import com.example.shopsphere.ui.screen.main.MainViewModel
import com.example.shopsphere.ui.screen.mycart.MyCartViewModel
import com.example.shopsphere.ui.screen.notification.NotificationViewModel
import com.example.shopsphere.ui.screen.ourproduct.OurProductViewModel
import com.example.shopsphere.ui.screen.payment.PaymentViewModel
import com.example.shopsphere.ui.screen.payment.success_payment.SuccessPaymentViewModel
import com.example.shopsphere.ui.screen.product.all.AllProductViewModel
import com.example.shopsphere.ui.screen.product.electronic.ElectronicProductViewModel
import com.example.shopsphere.ui.screen.product.jewelery.JeweleryProductViewModel
import com.example.shopsphere.ui.screen.product.men.MenProductViewModel
import com.example.shopsphere.ui.screen.product.women.WomenProductViewModel
import com.example.shopsphere.ui.screen.profile.ProfileViewModel
import com.example.shopsphere.ui.screen.registration.login.LoginViewModel
import com.example.shopsphere.ui.screen.registration.signup.SignUpViewModel
import com.example.shopsphere.ui.screen.registration.welcome.WelcomeViewModel
import com.example.shopsphere.ui.screen.yourorder.YourOrderViewModel

@Suppress("UNCHECKED_CAST", "UNREACHABLE_CODE")
class
ViewModelFactory(
    private val limit: Int = 20,
    private val repository: ShopSphereRepository,
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AllProductViewModel::class.java)){
            AllProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MenProductViewModel::class.java)) {
            MenProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(WomenProductViewModel::class.java)){
            WomenProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ElectronicProductViewModel::class.java)) {
            ElectronicProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(JeweleryProductViewModel::class.java)){
            JeweleryProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ForYouViewModel::class.java)){
            ForYouViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(OurProductViewModel::class.java)) {
            OurProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SignUpViewModel::class.java)){
            SignUpViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)){
            WelcomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MyCartViewModel::class.java)) {
            MyCartViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            FavouriteViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AddAddressViewModel::class.java)) {
            AddAddressViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AddressViewModel::class.java)) {
            AddressViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            CheckoutViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            PaymentViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SuccessPaymentViewModel::class.java)) {
            SuccessPaymentViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(YourOrderViewModel::class.java)) {
            YourOrderViewModel(repository) as T
        } else {
            NotificationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        fun getInstance(
            context: Context,
            limit: Int = 20,
        ) = ViewModelFactory(
            limit,
            Injection.provideRepository(context)
        )
    }
}