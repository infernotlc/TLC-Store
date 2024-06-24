package com.tlh.afinal.model.in_app_service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ProductAPIService, private val apiS: ProductAPI) {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products



    suspend fun fetchProductsFromNetwork() {
        try {
            val response = apiService.getData()
            Log.d("ProductRepository", "API Response: $response")

                _products.postValue(response.products)

        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching products", e)
        }
    }
    suspend fun addToCart(productId: Int, quantity: Int) {
        val request = CartItemRequest(productId, quantity)
        apiS.addToCart(request)
    }
}
