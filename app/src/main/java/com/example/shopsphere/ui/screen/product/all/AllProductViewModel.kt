package com.example.shopsphere.ui.screen.product.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.data.local.entity.Cart
import com.example.shopsphere.data.repository.ShopSphereRepository
import com.example.shopsphere.model.ProductResponseItem
import com.example.shopsphere.ui.common.UiState
import kotlinx.coroutines.launch

class AllProductViewModel(
    private val repository: ShopSphereRepository,
): ViewModel() {

    private val _uiState: MutableLiveData<UiState<List<ProductResponseItem>>> = MutableLiveData(UiState.Loading)
    val uiState: LiveData<UiState<List<ProductResponseItem>>> get() =  _uiState

    fun getAllProduct(limit: Int){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val product = repository.getAllProducts(limit)
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
}