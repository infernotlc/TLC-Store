package com.tlh.afinal.data.remote

import com.tlh.afinal.model.in_app_service.CartItemRequest
import com.tlh.afinal.model.in_app_service.CartResponse
import com.tlh.afinal.model.in_app_service.Category
import com.tlh.afinal.model.in_app_service.EditProfile
import com.tlh.afinal.model.in_app_service.OrderResponse
import com.tlh.afinal.model.in_app_service.ProductResponse
import com.tlh.afinal.model.in_app_service.Profile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductAPI {
    //main page products
    @GET("products")
    suspend fun getProducts(): ProductResponse

    //search products
    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): ProductResponse

    //categories request
    @GET("products/categories")
    suspend fun getCategories(): List<Category>

    //category products
    @GET("products/category/{slug}")
    suspend fun getProductsByCategory(@Path("slug") slug: String): ProductResponse

    //profile section
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): Profile

    //update profile
    @POST("users/{id}")
    suspend fun updateUser(@Path("id") userId: Int, @Body profile: EditProfile)

    //add to cart
    @POST("carts/add")
    suspend fun addToCart(@Body cartItem: CartItemRequest): CartResponse

    //get orders by user
    @GET("carts/user/{userId}")
    suspend fun getOrdersByUser(@Path("userId") userId: Int): OrderResponse
}
