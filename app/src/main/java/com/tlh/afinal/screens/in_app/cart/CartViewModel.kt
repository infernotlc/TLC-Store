package com.tlh.afinal.screens.in_app.cart

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlh.afinal.model.in_app_service.CartItemRequest
import com.tlh.afinal.model.in_app_service.CartResponse
import com.tlh.afinal.screens.in_app.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartResponse = MutableLiveData<CartResponse>()
    val cartResponse: LiveData<CartResponse> get() = _cartResponse

    fun addToCart(cartItem: CartItemRequest): LiveData<CartResponse> {
        viewModelScope.launch {
            try {
                val response = cartRepository.addToCart(cartItem)
                _cartResponse.value = response
            } catch (e: Exception) {
                Log.d(TAG, "addToCart: {${e.message}")
            }
        }
        return _cartResponse
    }
}
