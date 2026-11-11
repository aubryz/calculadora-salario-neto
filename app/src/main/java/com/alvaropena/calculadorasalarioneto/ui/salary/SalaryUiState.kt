package com.alvaropena.calculadorasalarioneto.ui.salary

import com.alvaropena.calculadorasalarioneto.ProfessionalGroup
import com.alvaropena.calculadorasalarioneto.SalaryResult

internal enum class MaritalStatus {
    SINGLE,
    MARRIED,
    DIVORCED,
    WIDOWED
}

internal data class SalaryUiState(
    val grossSalary: String = "",
    val numberOfPayments: String = "",
    val age: String = "",
    val numberOfChildren: String = "",
    val selectedProfessionalGroup: ProfessionalGroup? = null,
    val disabilityPercentage: String = "",
    val selectedMaritalStatus: MaritalStatus? = null,
    val result: SalaryResult? = null
) {
    val grossSalaryAmount = parseGrossSalary(grossSalary)
    val isGrossSalaryValid = grossSalaryAmount != null

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
        selectedProfessionalGroup != null &&
        selectedMaritalStatus != null
}
