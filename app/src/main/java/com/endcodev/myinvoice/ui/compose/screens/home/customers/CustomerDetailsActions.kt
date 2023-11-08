package com.endcodev.myinvoice.ui.compose.screens.home.customers

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

    CustomerDetailsScreen(
        onAcceptButton = {
            viewModel.saveCustomer()
            navController.navigate(Routes.CustomerContent.routes)
        },
        onCancelButton = { navController.navigate(Routes.CustomerContent.routes) },
        uiState,
        onUriChanged = { viewModel.updateUri(it) },
        onFiscalNameChange = {
            viewModel.onDataChanged(
                identifier = uiState.cIdentifier,
                fiscalName = it,
                telephone = uiState.cTelephone,
                country = uiState.cCountry,
                email = uiState.cEmail
            )
        },
        onIdentifierChange = {
            viewModel.onDataChanged(
                identifier = it,
                fiscalName = uiState.cFiscalName,
                telephone = uiState.cTelephone,
                country = uiState.cCountry,
                email = uiState.cEmail
            )
        },
        onCountryChange = {
            viewModel.onDataChanged(
                identifier = uiState.cIdentifier,
                fiscalName = uiState.cFiscalName,
                telephone = uiState.cTelephone,
                country = it,
                email = uiState.cEmail
            )
        },
        onEmailChange = {viewModel.onDataChanged(
            identifier = uiState.cIdentifier,
            fiscalName = uiState.cFiscalName,
            telephone = uiState.cTelephone,
            country = uiState.cCountry,
            email = it
        )}
    )
}