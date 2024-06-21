package com.tlh.afinal.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class ProductRoom(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val thumbnail: String,
    var isFavorite: Boolean
)
