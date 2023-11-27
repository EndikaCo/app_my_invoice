package com.endcodev.myinvoice.ui.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.ui.compose.components.UiText
import com.endcodev.myinvoice.data.network.AuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth : AuthenticationService

) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnabled = MutableLiveData(true)
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    private val errorChannel = Channel<UiText>()
    val errors = errorChannel.receiveAsFlow()

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isLoginEnabled.value = enableLogin(email, password)
    }

    private fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > MIN_PASS_LENGTH

    fun login() {
        val mail= _email.value
        val pass = _password.value

        if (pass != null  && mail != null) {
            auth.mailPassLogin(mail, pass, completionHandler = {
                viewModelScope.launch {
                if (it == 0)
                    errorChannel.send(UiText.StringResource(resId = R.string.no_error))
                else
                    errorChannel.send(UiText.StringResource(resId = R.string.error_mail_or_pass))}
            } )
        }
    }

    companion object {
        const val MIN_PASS_LENGTH = 6
    }
}
