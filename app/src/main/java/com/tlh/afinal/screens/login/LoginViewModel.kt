package com.tlh.afinal.screens.login


import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import com.tlh.afinal.SecondActivity
import com.tlh.afinal.common.ext.isValidEmail
import com.tlh.afinal.common.snackbar.SnackbarManager
import com.tlh.afinal.model.service.AccountService
import com.tlh.afinal.model.service.LogService
import com.tlh.afinal.screens.FinalViewModel

import dagger.hilt.android.lifecycle.HiltViewModel

import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject

import com.tlh.afinal.R.string as AppText


@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val appContext: android.content.Context,
    private val accountService: AccountService,
    logService: LogService,

    ) : FinalViewModel(logService) {
    var uiState = mutableStateOf(LoginUiState())
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

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)

            // Navigate to SecondActivity
            val intent = Intent(appContext, SecondActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            appContext.startActivity(intent)

        }
    }

    //password recovery
    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(AppText.recovery_email_sent)
        }
    }
}