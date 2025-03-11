package com.example.shopsphere.ui.screen.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shopsphere.data.local.entity.Favourite
import com.example.shopsphere.data.repository.ShopSphereRepository

class FavouriteViewModel(
    private val repository: ShopSphereRepository
) : ViewModel() {
    val allFavorites: LiveData<List<Favourite>> = repository.getAllFavourites()
}