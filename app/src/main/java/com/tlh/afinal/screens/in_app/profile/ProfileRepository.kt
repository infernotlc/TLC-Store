package com.tlh.afinal.screens.in_app.profile

import com.tlh.afinal.model.in_app_service.ProductAPI
import com.tlh.afinal.model.in_app_service.Profile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val userService: ProductAPI) {
    suspend fun getUser(userId: Int): Profile = userService.getUser(userId)
}
