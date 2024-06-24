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

data class OrderResponse(
    val carts: List<Order>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class Order(
    val id: Int,
    val products: List<Product>,
    val total: Double,
    val discountedTotal: Double,
    val userId: Int,
    val totalProducts: Int,
    val totalQuantity: Int
)

data class OrderProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val quantity: Int,
    val total: Double,
    val discountPercentage: Double,
    val discountedTotal: Double,
    val thumbnail: String
)