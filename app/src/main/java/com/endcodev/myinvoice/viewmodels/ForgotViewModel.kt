package com.endcodev.myinvoice.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor() : ViewModel(){

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _isForgotEnabled = MutableLiveData<Boolean>(false)
    val isForgotEnabled: LiveData<Boolean> = _isForgotEnabled

    fun onLoginChanged(email: String) {
        _email.value = email
        _isForgotEnabled.value = enableForgot(email)
    }

    private fun enableForgot(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
