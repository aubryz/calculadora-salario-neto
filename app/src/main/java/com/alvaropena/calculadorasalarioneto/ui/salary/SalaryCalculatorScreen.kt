package com.alvaropena.calculadorasalarioneto.ui.salary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun SalaryCalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: SalaryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val result = uiState.result

    if (result != null) {
        SalaryResultsScreen(
            result = result,
            onBack = viewModel::onBackToForm,
            modifier = modifier
        )
        return
    }

    SalaryFormScreen(
        uiState = uiState,
        onGrossSalaryChanged = viewModel::onGrossSalaryChanged,
        onNumberOfPaymentsChanged = viewModel::onNumberOfPaymentsChanged,
        onAgeChanged = viewModel::onAgeChanged,
        onNumberOfChildrenChanged = viewModel::onNumberOfChildrenChanged,
        onProfessionalGroupChanged = viewModel::onProfessionalGroupChanged,
        onDisabilityPercentageChanged = viewModel::onDisabilityPercentageChanged,
        onMaritalStatusChanged = viewModel::onMaritalStatusChanged,
        onCalculate = viewModel::onCalculate,
        modifier = modifier
    )
}
