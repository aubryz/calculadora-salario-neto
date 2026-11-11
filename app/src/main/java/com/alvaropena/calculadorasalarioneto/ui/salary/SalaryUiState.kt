package com.alvaropena.calculadorasalarioneto.ui.salary

import com.alvaropena.calculadorasalarioneto.SalaryResult

internal data class SalaryUiState(
    val grossSalary: String = "",
    val numberOfPayments: String = "",
    val age: String = "",
    val numberOfChildren: String = "",
    val selectedProfessionalGroup: String = "",
    val disabilityPercentage: String = "",
    val selectedMaritalStatus: String = "",
    val result: SalaryResult? = null
) {
    val grossSalaryAmount = grossSalary.trim().replace(',', '.').toBigDecimalOrNull()
    val isGrossSalaryValid = grossSalaryAmount != null && grossSalaryAmount.signum() > 0

    private val paymentCount = numberOfPayments.trim().toIntOrNull()
    val isPaymentCountValid = paymentCount != null && paymentCount > 0

    private val ageYears = age.trim().toIntOrNull()
    val isAgeValid = ageYears != null && ageYears > 0

    private val childCount = numberOfChildren.trim().toIntOrNull()
    val isChildCountValid = childCount != null && childCount >= 0

    private val disabilityValue = disabilityPercentage.trim().toIntOrNull()
    val isDisabilityValid = disabilityValue != null && disabilityValue in 0..100

    val canCalculate = isGrossSalaryValid &&
        isPaymentCountValid &&
        isAgeValid &&
        isChildCountValid &&
        isDisabilityValid &&
        selectedProfessionalGroup in professionalGroups &&
        selectedMaritalStatus in maritalStatuses

    companion object {
        val professionalGroups = listOf("Grupo 1", "Grupo 2", "Grupo 3")
        val maritalStatuses = listOf("Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a")
    }
}
