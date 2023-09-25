package com.endcodev.myinvoice.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.UiText
import com.endcodev.myinvoice.network.AuthError
import com.endcodev.myinvoice.network.AuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth : AuthenticationService
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _repeatPassword = MutableLiveData<String>()
    val repeatPassword: LiveData<String> = _repeatPassword

    private val _isSignInEnabled = MutableLiveData<Boolean>(false)
    val isSignUpEnabled: LiveData<Boolean> = _isSignInEnabled

    fun onSignUpChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isSignInEnabled.value = isValidCredentials(email, password)
    }

    fun isValidCredentials(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

    fun createAccount(){
        val mail= _email.value
        val pass = _password.value
        if (pass != null  && mail != null) {
            auth.createUser(mail, pass, onCreateUser = {
                if (it == AuthError.NoError.error)
                    UiText.StringResource(resId = R.string.no_error)
            })
        }
    }
}
