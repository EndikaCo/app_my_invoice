package com.endcodev.myinvoice.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.UiText
import com.endcodev.myinvoice.network.AuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth : AuthenticationService

) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnabled = MutableLiveData<Boolean>(false)
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isLoginEnabled.value = enableLogin(email, password)
    }

    fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

    fun login() {

        val mail= _email.value
        val pass = _password.value

        if (pass != null  && mail != null) {
            auth.mailPassLogin(mail, pass, completionHandler = {
                if (it == 0)
                    UiText.StringResource(resId = R.string.no_error)
                else
                    UiText.StringResource(resId = R.string.error_mail_or_pass)
            } )
        }

    }
}