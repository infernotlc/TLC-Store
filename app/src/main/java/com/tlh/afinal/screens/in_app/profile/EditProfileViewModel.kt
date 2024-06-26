package com.tlh.afinal.screens.in_app.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlh.afinal.model.in_app_service.EditProfile
import com.tlh.afinal.model.in_app_service.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val repository: ProfileRepository) : ViewModel() {

    private val _user = MutableLiveData<Profile>()
    val user: LiveData<Profile> get() = _user

    fun fetchUser(userId: Int) {
        viewModelScope.launch {
            try {
                val fetchedUser = repository.getUser(userId)
                _user.value = fetchedUser
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    fun updateUser(profile: EditProfile) {
        viewModelScope.launch {
            try {
                repository.updateUser(profile.id, profile)
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
}
