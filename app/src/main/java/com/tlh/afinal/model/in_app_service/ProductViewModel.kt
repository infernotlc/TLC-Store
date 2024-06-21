package com.tlh.afinal.model.in_app_service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    val products: LiveData<List<Product>> get() = repository.products

    init {
        viewModelScope.launch {
            repository.fetchProductsFromNetwork()
            repository.products.observeForever {
                Log.d("ProductViewModel", "Fetched products: $it")
            }
        }

    }

    // Function to add product to cart
    fun addToCart(productId: Int, quantity: Int) {
        viewModelScope.launch {
            try {
                repository.addToCart(productId, quantity)
                Log.d("ProductViewModel", "Product added to cart: $productId with quantity: $quantity")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error adding product to cart: ${e.message}", e)
            }
        }
    }
}

