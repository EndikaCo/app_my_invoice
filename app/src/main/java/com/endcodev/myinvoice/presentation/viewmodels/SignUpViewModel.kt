package com.endcodev.myinvoice.presentation.viewmodels

import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.presentation.compose.components.UiText
import com.endcodev.myinvoice.data.network.AuthError.*
import com.endcodev.myinvoice.data.network.AuthenticationService
import com.endcodev.myinvoice.data.network.FirebaseClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: AuthenticationService,
    private val client: FirebaseClient
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

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun onSignUpChanged(email: String, password: String, repeat:String) {
        _email.value = email
        _password.value = password
        _repeatPassword.value = repeat
        _isSignInEnabled.value = isValidCredentials(email, password, repeat)
    }

    private fun isValidCredentials(email: String, password: String, repeat: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6 && password == repeat

    fun createAccount() {
        val mail = _email.value
        val pass = _password.value
        if (pass != null && mail != null) {

            auth.createUser(mail, pass, onCreateUser = {

                if (it == NoError.error)
                    listenerMailVerification()

                sendToUi(it)
            })
        }
    }

    private fun sendToUi(error: Int) {
        viewModelScope.launch {
            if (error == NoError.error)
                errorChannel.send(UiText.StringResource(resId = R.string.no_error))
            if (error == ErrorCreatingAccount.error)
                errorChannel.send(UiText.StringResource(resId = R.string.error_creating_account))
        }
    }

    private fun listenerMailVerification() {

        val handler = Handler(Looper.getMainLooper())

        val runnableCode: Runnable = object : Runnable {
            override fun run() {

                client.auth.currentUser?.reload()

                val mClient = client.auth.currentUser

                if (mClient?.isEmailVerified == false) { // Check user's email verified
                    _isLoading.postValue(true)

                    handler.postDelayed(this, 2000) // Repeat block every 2s
                } else{
                    _isLoading.postValue(false)
                    _isSuccess.postValue(true)
                }
            }
        }
        handler.post(runnableCode)
    }
}