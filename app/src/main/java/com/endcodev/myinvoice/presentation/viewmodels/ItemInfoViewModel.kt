package com.endcodev.myinvoice.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.myinvoice.data.database.entities.ProductEntity
import com.endcodev.myinvoice.domain.models.product.Product
import com.endcodev.myinvoice.domain.models.product.ProductUiState
import com.endcodev.myinvoice.domain.usecases.GetSimpleItemsUseCase
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
            val item = getSimpleItemUseCase.invoke(itemId)
            withContext(Dispatchers.Main) {
                if (item != null)
                    updateUi(item)
            }
        }
    }

    private fun updateUi(item: Product) {
        _uiState.update { currentState ->
            currentState.copy(
                iCode = item.id,
                iName = item.name,
                iImage = item.image,
                isLoading = false,
                isAcceptEnabled = enableAccept(item.id, item.name)
            )
        }
    }

    fun onDataChanged(
        code: String,
        name: String,
        image : Uri?,
        type: String,
        cost: Float,
        price : Float,
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                iName = name,
                iCode = code,
                iImage = image,
                iType = type,
                iPrice = price,
                iCost = cost,
                isAcceptEnabled = enableAccept(code, name)
            )
        }
    }

    fun saveItem( ) {
        viewModelScope.launch {
            with(_uiState.value) {
                Log.v("ItemInfoViewModel", "saveItem: ${iImage.toString()}")
                val item = ProductEntity(
                    image = iImage.toString(),
                    id = iCode,
                    name = iName,
                    description = "", //todo,
                    type = "",
                    price = iPrice,
                    stock = 0f,
                    cost = iCost
                )
                getSimpleItemUseCase.saveItem(item)
            }
        }
    }

    private fun enableDelete(itemId: String?) : Boolean{
        return itemId != null
    }

    private fun enableAccept(code: String, name: String) =
        code.isNotEmpty() && name.isNotEmpty()

    fun deleteItem() {
        TODO("Not yet implemented")
    }
}