package com.endcodev.myinvoice.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.database.entities.ProductEntity
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.domain.models.product.ProductUiState
import com.endcodev.myinvoice.domain.usecases.GetSimpleItemsUseCase
import com.endcodev.myinvoice.domain.utils.App
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    private val getSimpleItemUseCase: GetSimpleItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    fun getItem(itemId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val item = getSimpleItemUseCase.invoke(itemId)
                withContext(Dispatchers.Main) {
                    if (item != null)
                        updateUi(item)
                }
            } catch (e: Exception) {
                Log.e(App.tag, e.message.toString())
            }
        }
    }

    private fun updateUi(item: Product) {
        _uiState.update { currentState ->
            currentState.copy(
                id = item.id,
                name = item.name,
                image = item.image,
                type = item.type,
                price = item.price,
                cost = item.cost,
                stock = item.stock,
                isLoading = false,
                isAcceptEnabled = enableAccept(item.id, item.name),
                isDeleteEnabled = true
            )
        }
    }

    fun onDataChanged(
        code: String,
        name: String,
        image: Uri?,
        type: String,
        cost: Float,
        price: Float,
        stock: Float
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name,
                id = code,
                image = image,
                type = type,
                price = price,
                cost = cost,
                stock = stock,
                isAcceptEnabled = enableAccept(code, name)
            )
        }
    }

    private fun enableAccept(code: String, name: String) =
        code.isNotEmpty() && name.isNotEmpty()

    fun saveItem() {
        viewModelScope.launch(Dispatchers.IO) {

            val uiStateValue = _uiState.value
            val item = ProductEntity(
                image = uiStateValue.image.toString(),
                id = uiStateValue.id,
                name = uiStateValue.name,
                description = "-",
                type = uiStateValue.type,
                price = uiStateValue.price,
                stock = uiStateValue.stock,
                cost = uiStateValue.cost
            )
            getSimpleItemUseCase.saveItem(item)
        }
    }

    fun deleteItem() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSimpleItemUseCase.deleteProduct(_uiState.value.id)
            } catch (e: Exception) {
                Log.e(App.tag, e.message.toString())
            }
        }
    }
}