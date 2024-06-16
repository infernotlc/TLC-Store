package com.tlh.afinal.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.tlh.afinal.LOGIN_SCREEN
import com.tlh.afinal.SETTINGS_SCREEN
import com.tlh.afinal.model.service.AccountService
import com.tlh.afinal.model.service.LogService
import com.tlh.afinal.screens.FinalViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.tlh.afinal.SPLASH_SCREEN
import com.tlh.afinal.model.service.ConfigurationService

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService
) : FinalViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        if (accountService.hasUser) openAndPopUp(SETTINGS_SCREEN, SPLASH_SCREEN)
        else createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUp(SETTINGS_SCREEN, SPLASH_SCREEN)
        }
    }
}
