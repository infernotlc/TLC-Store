package com.tlh.afinal.screens.in_app.search_product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlh.afinal.model.in_app_service.Product
import com.tlh.afinal.data.remote.ProductAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(
    private val productService: ProductAPI
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Product>>()
    val searchResults: LiveData<List<Product>> get() = _searchResults

    fun searchProducts(query: String) {
        viewModelScope.launch {
            try {
                val productResponse = productService.searchProducts(query)
                val products = productResponse.products
                _searchResults.postValue(products)
            } catch (e: Exception) {
                Log.e(TAG, "Error searching products: ${e.message}", e)
                // Handle error as needed
            }
        }
    }

    companion object {
        private const val TAG = "SearchProductViewModel"
    }
}
