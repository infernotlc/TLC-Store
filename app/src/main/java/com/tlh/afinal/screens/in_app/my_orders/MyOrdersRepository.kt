package com.tlh.afinal.screens.in_app.my_orders

import com.tlh.afinal.model.in_app_service.Order
import com.tlh.afinal.data.remote.ProductAPI
import javax.inject.Inject

class MyOrdersRepository @Inject constructor(private val apiService: ProductAPI) {

    suspend fun fetchOrdersForUser(userId: Int): List<Order> {
        return apiService.getOrdersByUser(userId).carts
    }
}
