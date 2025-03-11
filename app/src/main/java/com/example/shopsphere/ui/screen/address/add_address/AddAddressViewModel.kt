package com.example.shopsphere.ui.screen.address.add_address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsphere.data.local.entity.UserLocation
import com.example.shopsphere.data.repository.ShopSphereRepository
import com.example.shopsphere.ui.common.UiState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class AddAddressViewModel(
    private val repository: ShopSphereRepository
) : ViewModel() {

    private val _locationName = MutableLiveData<String>()
    val locationName: LiveData<String> = _locationName

    private val _uiState = MutableLiveData<UiState<LatLng>>()
    val uiState: LiveData<UiState<LatLng>> = _uiState

    fun addUsersLocation(name: String, address: String) {
        viewModelScope.launch {
            val userLocation = UserLocation(
                name = name,
                address = address
            )
            repository.addUsersLocation(userLocation)
        }
    }

    fun getCurrentLocation(){
        _uiState.value = UiState.Loading
        repository.getCurrentLocation().observeForever { location ->
            if (location != null) {
                try {
                    viewModelScope.launch {
                        val address = repository.getAddressFromLatLng(location)
                        _uiState.value = UiState.Success(location)
                        _locationName.value = address
                    }
                } catch (e: Exception) {
                    _uiState.value = UiState.Error(e.message ?: "Unknown error")
                }
            } else {
                _uiState.value = UiState.Error("No location data available")
            }
        }
    }

    fun getInitialLocationName(latLng: LatLng) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val address = repository.getAddressFromLatLng(latLng)
                _uiState.value = UiState.Success(latLng)
                _locationName.value = address
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to get address: ${e.message}")
            }
        }
    }

}