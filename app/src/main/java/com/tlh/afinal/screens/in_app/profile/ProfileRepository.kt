package com.tlh.afinal.screens.in_app.profile

import com.tlh.afinal.data.remote.ProductAPI
import com.tlh.afinal.model.in_app_service.EditProfile
import com.tlh.afinal.model.in_app_service.Profile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val userService: ProductAPI) {

    suspend fun getUser(userId: Int): Profile {
        return userService.getUser(userId)
    }

    suspend fun updateUser(userId: Int, user: EditProfile) {
        userService.updateUser(userId, user)
    }
}
