package com.endcodev.myinvoice.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.R
import com.endcodev.myinvoice.data.network.auth.AuthenticationService
import com.endcodev.myinvoice.data.network.auth.FirebaseClient
import com.endcodev.myinvoice.presentation.compose.components.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val auth: AuthenticationService,
    firebaseClient: FirebaseClient
) : ViewModel() {

 fun disconnectUser() {
        auth.disconnectUser()
    }
}
