package com.tlh.afinal.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RoomProductRepository
) : ViewModel() {

    val favoriteProducts: LiveData<List<ProductRoom>> = repository.favoriteProducts

    fun addFavoriteProduct(product: ProductRoom) {
        viewModelScope.launch {
            repository.addFavoriteProduct(product)
        }
    }

    fun removeFavoriteProduct(productId: Int) {
        viewModelScope.launch {
            repository.removeFavoriteProduct(productId)
        }
    }
}
