package com.alvaropena.calculadorasalarioneto

import java.math.BigDecimal
import java.math.RoundingMode

fun calculateIncomeTax(grossSalary: BigDecimal): BigDecimal {
    val simulatedTaxRate = BigDecimal("0.15")
    val incomeTax = grossSalary * simulatedTaxRate
    return incomeTax.setScale(2, RoundingMode.HALF_UP)
}

data class SalaryResult(
    val grossSalary: BigDecimal,
    val incomeTax: BigDecimal,
    val otherDeductions: BigDecimal,
    val netSalary: BigDecimal
)

fun calculateSalary(
    grossSalary: BigDecimal,
    professionalGroup: String
): SalaryResult {
    val incomeTax = calculateIncomeTax(grossSalary)
    val otherDeductions = calculateOtherDeductions(grossSalary, professionalGroup)
    val netSalary = grossSalary - incomeTax - otherDeductions

    return SalaryResult(
        grossSalary = grossSalary,
        incomeTax = incomeTax,
        otherDeductions = otherDeductions,
        netSalary = netSalary
    )
}

fun calculateOtherDeductions(
    grossSalary: BigDecimal,
    professionalGroup: String
): BigDecimal {
    val deductionRate = when (professionalGroup) {
        "Grupo 1" -> BigDecimal("0.01")
        "Grupo 2" -> BigDecimal("0.02")
        "Grupo 3" -> BigDecimal("0.03")
        else -> throw IllegalArgumentException("Unknown professional group")
    }

    val deductions = grossSalary * deductionRate
    return deductions.setScale(2, RoundingMode.HALF_UP)
}