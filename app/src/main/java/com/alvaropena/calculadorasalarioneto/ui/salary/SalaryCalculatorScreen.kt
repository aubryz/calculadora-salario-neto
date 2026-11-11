package com.alvaropena.calculadorasalarioneto.ui.salary

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
/**
 * Conecta el estado de la calculadora con el formulario y los resultados.
 * Envía al ViewModel las acciones del usuario.
 */
@Composable
internal fun SalaryCalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: SalaryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // El resultado determina la pantalla activa. Al borrarlo, se vuelve al formulario con los valores ya introducidos.
    val result = uiState.result
    // El botón Atrás de Android usa la misma acción que «Volver» en los resultados.
    BackHandler(enabled = result != null, onBack = viewModel::onBackToForm)

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
