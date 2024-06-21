package com.tlh.afinal.model.in_app_service

import com.google.gson.annotations.SerializedName


data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    @SerializedName("discountPercentage") val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val tags: List<String>,//
    val brand: String,
    val sku: String,
    val weight: Int,
    val dimensions: Dimensions,
    @SerializedName("warrantyInformation") val warrantyInformation: String,
    @SerializedName("shippingInformation") val shippingInformation: String,
    @SerializedName("availabilityStatus") val availabilityStatus: String,
    val reviews: List<Review>,
    @SerializedName("returnPolicy") val returnPolicy: String,
    @SerializedName("minimumOrderQuantity") val minimumOrderQuantity: Int,
    val meta: Meta,
    val images: List<String>,
    val thumbnail: String,
    var isFavorite: Boolean = false
)

data class Dimensions(
    val width: Double,
    val height: Double,
    val depth: Double
)

data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    @SerializedName("reviewerName") val reviewerName: String,
    @SerializedName("reviewerEmail") val reviewerEmail: String
)

data class Meta(
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    val barcode: String,
    @SerializedName("qrCode") val qrCode: String
)

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

