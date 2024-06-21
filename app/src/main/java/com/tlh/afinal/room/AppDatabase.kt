package com.tlh.afinal.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductRoom::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
