package com.endcodev.myinvoice.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.UiText
import com.endcodev.myinvoice.network.AuthenticationService
import com.endcodev.myinvoice.network.FirebaseClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val auth : FirebaseClient
) : ViewModel(){

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _isForgotEnabled = MutableLiveData<Boolean>(false)
    val isForgotEnabled: LiveData<Boolean> = _isForgotEnabled

    private val errorChannel = Channel<UiText>()
    val errors = errorChannel.receiveAsFlow()

    fun onLoginChanged(email: String) {
        _email.value = email
        _isForgotEnabled.value = enableForgot(email)
    }

    private fun enableForgot(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun forgotPass() {

        val mail = _email.value
        if (mail != null) {

            resetPass(mail, listenerUnit = {
                viewModelScope.launch {
                    if (it == 0)
                        errorChannel.send(UiText.StringResource(resId = R.string.pass_changed))
                    else
                        errorChannel.send(UiText.StringResource(resId = R.string.pass_change_error))
                }
            })
        }
    }

     private fun resetPass(mail : String, listenerUnit : (Int) -> Unit){
        auth.auth.sendPasswordResetEmail(mail)
            .addOnSuccessListener {
                listenerUnit(0)
            }
            .addOnFailureListener {
                listenerUnit(1)
            }
     }
}

