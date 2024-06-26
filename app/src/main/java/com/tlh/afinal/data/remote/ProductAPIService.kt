package com.tlh.afinal.data.remote

import com.tlh.afinal.model.in_app_service.ProductResponse
import javax.inject.Inject

class ProductAPIService @Inject constructor(private val api: ProductAPI) {

    suspend fun getData(): ProductResponse {
        return api.getProducts()
    }

}
