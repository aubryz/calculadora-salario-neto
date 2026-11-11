package com.alvaropena.calculadorasalarioneto.ui.salary

import androidx.lifecycle.ViewModel
import com.alvaropena.calculadorasalarioneto.ProfessionalGroup
import com.alvaropena.calculadorasalarioneto.calculateSalary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
/**
 * Mantiene el estado del formulario y responde a las acciones del usuario.
 * Solicita el cálculo cuando los datos son válidos y publica el resultado.
 */
internal class SalaryViewModel : ViewModel() {
    private val mutableUiState = MutableStateFlow(SalaryUiState())
    // Expone el estado como un flujo de solo lectura para que la interfaz pueda observarlo.
    val uiState = mutableUiState.asStateFlow()

    fun onGrossSalaryChanged(value: String) {
        mutableUiState.update { it.copy(grossSalary = value) }
    }

    fun onNumberOfPaymentsChanged(value: String) {
        mutableUiState.update { it.copy(numberOfPayments = value) }
    }

    fun onAgeChanged(value: String) {
        mutableUiState.update { it.copy(age = value) }
    }

    fun onNumberOfChildrenChanged(value: String) {
        mutableUiState.update { it.copy(numberOfChildren = value) }
    }

    fun onProfessionalGroupChanged(value: ProfessionalGroup) {
        mutableUiState.update { it.copy(selectedProfessionalGroup = value) }
    }

    fun onDisabilityPercentageChanged(value: String) {
        mutableUiState.update { it.copy(disabilityPercentage = value) }
    }

    fun onMaritalStatusChanged(value: MaritalStatus) {
        mutableUiState.update { it.copy(selectedMaritalStatus = value) }
    }

    fun onCalculate() {
        mutableUiState.update { state ->
            val grossSalaryAmount = state.grossSalaryAmount
            val professionalGroup = state.selectedProfessionalGroup
            if (!state.canCalculate || grossSalaryAmount == null || professionalGroup == null) {
                return@update state
            }

            val result = calculateSalary(
                grossSalary = grossSalaryAmount,
                professionalGroup = professionalGroup
            )
            state.copy(result = result)
        }
    }

    fun onBackToForm() {
        // Al limpiar el resultado, se vuelve al formulario con los datos ya introducidos.
        mutableUiState.update { it.copy(result = null) }
    }
}
