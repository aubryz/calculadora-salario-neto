package com.alvaropena.calculadorasalarioneto.ui.salary

import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SalaryAmountParserTest {
    @Test
    fun acceptsPlainAmountsWithCommaOrDotAsDecimalSeparator() {
        assertEquals(BigDecimal("30000"), parseGrossSalary("30000"))
        assertEquals(BigDecimal("30000.50"), parseGrossSalary("30000,50"))
        assertEquals(BigDecimal("30000.50"), parseGrossSalary("30000.50"))
    }

    @Test
    fun acceptsSpanishThousandsGrouping() {
        assertEquals(BigDecimal("30000"), parseGrossSalary("30.000"))
        assertEquals(BigDecimal("30000.50"), parseGrossSalary("30.000,50"))
        assertEquals(BigDecimal("1234567.89"), parseGrossSalary(" 1.234.567,89 "))
    }

    @Test
    fun rejectsIncompleteMalformedAndNonPositiveAmounts() {
        val invalidAmounts = listOf("", "0", "0,00", "-1", "30.00,50", "1,234", "30000,", "3e4")
        invalidAmounts.forEach { amount ->
            assertNull("Invalid salary: $amount", parseGrossSalary(amount))
        }
    }
}
