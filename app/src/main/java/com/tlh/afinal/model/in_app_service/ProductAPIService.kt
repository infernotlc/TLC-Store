package com.tlh.afinal.model.in_app_service

import javax.inject.Inject

class ProductAPIService @Inject constructor(private val api: ProductAPI) {

    suspend fun getData(): ProductResponse {
        return api.getProducts()
    }

}
