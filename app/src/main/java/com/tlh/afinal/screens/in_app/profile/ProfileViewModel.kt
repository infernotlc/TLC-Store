package com.tlh.afinal.screens.in_app.profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlh.afinal.model.in_app_service.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: ProfileRepository) : ViewModel() {

    private val _user = MutableLiveData<Profile>()
    val user: LiveData<Profile> get() = _user

    fun fetchUser(userId: Int) {
        viewModelScope.launch {
            try {
                val fetchedUser = userRepository.getUser(userId)
                _user.value = fetchedUser
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
}
