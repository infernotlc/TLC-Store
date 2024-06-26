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
class ProfileViewModel @Inject constructor(private val userRepository: ProfileRepository) : ViewModel() {

    private val _user = MutableLiveData<Profile>()
    val user: LiveData<Profile> get() = _user

    // Profil bilgilerini yükleme metodu
    fun fetchUser(userId: Int) {
        viewModelScope.launch {
            try {
                val fetchedUser = userRepository.getUser(userId)
                _user.value = fetchedUser
            } catch (e: Exception) {
                // Hata durumunu handle et
            }
        }
    }
    fun updateUser(profile: EditProfile) {
        viewModelScope.launch {
            try {
                userRepository.updateUser(profile.id, profile)
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

}
