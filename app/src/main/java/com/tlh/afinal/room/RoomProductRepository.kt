package com.tlh.afinal.room

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomProductRepository @Inject constructor(
    private val productDao: ProductDao
) {

    val favoriteProducts: LiveData<List<ProductRoom>> = productDao.getFavoriteProducts()

    suspend fun addFavoriteProduct(product: ProductRoom) {
        productDao.insertProduct(product)
    }

    suspend fun removeFavoriteProduct(productId: Int) {
        productDao.deleteProductById(productId)
    }
}
