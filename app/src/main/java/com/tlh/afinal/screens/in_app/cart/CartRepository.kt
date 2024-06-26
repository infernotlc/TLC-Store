package com.tlh.afinal.screens.in_app.cart

import com.tlh.afinal.model.in_app_service.CartItemRequest
import com.tlh.afinal.model.in_app_service.CartResponse
import com.tlh.afinal.data.remote.ProductAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(private val cartService: ProductAPI) {
    suspend fun addToCart(cartItem: CartItemRequest): CartResponse = cartService.addToCart(cartItem)


}
