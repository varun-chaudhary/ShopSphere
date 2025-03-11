package com.example.shopsphere.ui.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.data.local.entity.Cart
import com.example.shopsphere.data.local.entity.Favourite
import com.example.shopsphere.data.repository.ShopSphereRepository
import com.example.shopsphere.model.ProductResponseItem
import com.example.shopsphere.ui.common.UiState
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ShopSphereRepository,
): ViewModel(){

    private val _uiState: MutableLiveData<UiState<ProductResponseItem>> = MutableLiveData(UiState.Loading)
    val uiState: LiveData<UiState<ProductResponseItem>> get() =  _uiState

    val favouriteItems: LiveData<List<Favourite>> = repository.getAllFavourites()

    fun getSingleProduct(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val product = repository.getSingleProduct(id)
                _uiState.value = UiState.Success(product)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun addToCart(product: ProductResponseItem) {
        viewModelScope.launch{
            val cartItems = Cart(
                productId = product.id,
                productName = product.title,
                productPrice = product.price.toString(),
                productImage = product.image,
                productCategory = product.category,
                productQuantity = 1
            )
            repository.addToCart(cartItems)
        }
    }

    fun addToFavourite(product: ProductResponseItem) {
        viewModelScope.launch {
            val favourite = Favourite(
                productId = product.id,
                productName = product.title,
                productPrice = product.price.toString(),
                productImage = product.image,
                productCategory = product.category,
                productQuantity = 1
            )
            repository.addToFavourite(favourite)
        }
    }

    fun isProductFavorited(productId: Int): LiveData<Boolean> {
        return repository.isProductFavorited(productId)
    }


    fun deleteFavouriteById(favourite: Favourite) {
        viewModelScope.launch {
            repository.deleteFavouriteById(favourite.id)
        }
    }
}