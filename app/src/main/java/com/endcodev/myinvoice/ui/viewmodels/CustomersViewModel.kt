package com.endcodev.myinvoice.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.model.CustomerModel
import com.endcodev.myinvoice.domain.GetPlayersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayersUseCase,
) : ViewModel() {

    private val _customersList = MutableLiveData<MutableList<CustomerModel>>()
    val customersList: LiveData<MutableList<CustomerModel>> get() = _customersList

    val isLoading = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            isLoading.postValue(true)
            _customersList.value = getPlayersUseCase.invoke()?.toMutableList()
            isLoading.postValue(false)
        }
    }
}