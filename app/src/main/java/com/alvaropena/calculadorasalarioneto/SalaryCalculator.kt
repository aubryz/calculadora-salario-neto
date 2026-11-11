package com.alvaropena.calculadorasalarioneto

import java.math.BigDecimal
import java.math.RoundingMode
/**
 * Aplica la retención fija del 15 % definida para la simulación del ejercicio.
 */
fun calculateIncomeTax(grossSalary: BigDecimal): BigDecimal {
    val simulatedTaxRate = BigDecimal("0.15")
    val incomeTax = grossSalary * simulatedTaxRate
    return incomeTax.setScale(2, RoundingMode.HALF_UP)
}

enum class ProfessionalGroup {
    GROUP_1,
    GROUP_2,
    GROUP_3
}

data class SalaryResult(
    val grossSalary: BigDecimal,
    val incomeTax: BigDecimal,
    val otherDeductions: BigDecimal,
    val netSalary: BigDecimal
)
/**
 * Calcula el salario neto anual restando el IRPF y las otras deducciones.
 * Cada deducción se redondea a céntimos antes de restarla del salario bruto.
 */
fun calculateSalary(
    grossSalary: BigDecimal,
    professionalGroup: ProfessionalGroup
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
    professionalGroup: ProfessionalGroup
): BigDecimal {
    val deductionRate = when (professionalGroup) {
        ProfessionalGroup.GROUP_1 -> BigDecimal("0.01")
        ProfessionalGroup.GROUP_2 -> BigDecimal("0.02")
        ProfessionalGroup.GROUP_3 -> BigDecimal("0.03")
    }

    val deductions = grossSalary * deductionRate
    return deductions.setScale(2, RoundingMode.HALF_UP)
}