package com.alvaropena.calculadorasalarioneto.ui.salary

import java.math.BigDecimal
/**
 * Convierte un salario positivo escrito con formato simple o español
 * con separadores de miles. Devuelve null si el importe no es válido.
 */
private val groupedSalaryPattern = Regex("""[1-9][0-9]{0,2}(?:\.[0-9]{3})+(?:,[0-9]{1,2})?""")
private val plainSalaryPattern = Regex("""[0-9]+(?:[,.][0-9]{1,2})?""")

internal fun parseGrossSalary(input: String): BigDecimal? {
    val amount = input.trim()
    // Valida la agrupación de miles antes de quitar los puntos.
    val normalizedAmount = when {
        groupedSalaryPattern.matches(amount) -> amount.replace(".", "").replace(',', '.')
        plainSalaryPattern.matches(amount) -> amount.replace(',', '.')
        else -> return null
    }

    return normalizedAmount.toBigDecimalOrNull()?.takeIf { it.signum() > 0 }
}
