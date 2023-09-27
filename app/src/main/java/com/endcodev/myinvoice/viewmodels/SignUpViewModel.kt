package com.endcodev.myinvoice.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.UiText
import com.endcodev.myinvoice.network.AuthError.*
import com.endcodev.myinvoice.network.AuthenticationService
import com.endcodev.myinvoice.network.FirebaseClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth : AuthenticationService,
    private val client : FirebaseClient
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _repeatPassword = MutableLiveData<String>()
    val repeatPassword: LiveData<String> = _repeatPassword

    private val _isSignInEnabled = MutableLiveData(false)
    val isSignUpEnabled: LiveData<Boolean> = _isSignInEnabled

    private val errorChannel = Channel<UiText>()
    val errors = errorChannel.receiveAsFlow()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onSignUpChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isSignInEnabled.value = isValidCredentials(email, password)
    }

    fun isValidCredentials(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

    fun createAccount() {
        val mail = _email.value
        val pass = _password.value
        if (pass != null && mail != null) {
            auth.createUser(mail, pass, onCreateUser = {

                viewModelScope.launch {
                    if (it == NoError.error)
                        auth.mailPassLogin(mail, pass, completionHandler = {})
                        checkVerification()
                        errorChannel.send(UiText.StringResource(resId = R.string.no_error))
                    if (it == ErrorCreatingAccount.error)
                        errorChannel.send(UiText.StringResource(resId = R.string.error_creating_account))
                }

            })
        }
    }

    private fun checkVerification() {
        val client = client.auth.currentUser
        Log.v("***", client.toString())
        if (client?.isEmailVerified == false){
            Log.v("***", "${client.isEmailVerified}")

            _isLoading.postValue(true)
        }
    }
}