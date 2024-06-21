package com.tlh.afinal.model.in_app_service

data class CartResponse(
    val id: Int,
    val products: List<CartProduct>,
    val total: Int,
    val discount: Int,
    val userId: Int
)

data class CartProduct(
    val id: Int,
    val title: String,
    val price: Int,
    val quantity: Int
)
