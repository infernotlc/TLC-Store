package com.tlh.afinal.screens.sign_up


import androidx.compose.runtime.mutableStateOf
import com.tlh.afinal.SETTINGS_SCREEN
import com.tlh.afinal.SIGN_UP_SCREEN
import com.tlh.afinal.common.ext.isValidEmail
import com.tlh.afinal.common.ext.isValidPassword
import com.tlh.afinal.common.ext.passwordMatches
import com.tlh.afinal.common.snackbar.SnackbarManager
import com.tlh.afinal.model.service.AccountService
import com.tlh.afinal.model.service.LogService
import com.tlh.afinal.screens.FinalViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.tlh.afinal.R.string as AppText

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : FinalViewModel(logService) {
    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            accountService.linkAccount(email, password)
            openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
        }
    }
}