package com.endcodev.myinvoice.ui.compose.screens.home.customers.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endcodev.myinvoice.ui.navigation.Routes
import com.endcodev.myinvoice.ui.viewmodels.CustomerInfoViewModel

/**
 * Handles actions related to customer details.
 * @param customerIdentifier The identifier of the customer.
 * @param navController The navigation controller used for navigation.
 */
@Composable
fun CustomerDetailsActions(
    customerIdentifier: String?,
    navController: NavHostController
) {

    val viewModel: CustomerInfoViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (customerIdentifier != null) {
        LaunchedEffect(customerIdentifier) {
            viewModel.getCustomer(customerIdentifier)
        }
    }

    fun onUpdateData(
        identifier: String? = null,
        fiscalName: String? = null,
        telephone: String? = null,
        country: String? = null,
        email: String? = null
    ) {
        viewModel.onDataChanged(
            identifier = identifier ?: uiState.cIdentifier,
            fiscalName = fiscalName ?: uiState.cFiscalName,
            telephone = telephone ?: uiState.cTelephone,
            country = country ?: uiState.cCountry,
            email = email ?: uiState.cEmail
        )
    }

    CustomerDetailsScreen(
        onAcceptButton = {
            viewModel.saveCustomer()
            navController.navigate(Routes.CustomerContent.routes)
        },
        onCancelButton = { navController.navigate(Routes.CustomerContent.routes) },
        uiState,
        onUriChanged = { viewModel.updateUri(it) },
        onFiscalNameChange = { onUpdateData(fiscalName = it) },
        onIdentifierChange = { onUpdateData(identifier = it) },
        onCountryChange = { onUpdateData(country = it) },
        onEmailChange = { onUpdateData(email = it) }
    )
}



