package com.alvaropena.calculadorasalarioneto

import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Test

class SalaryCalculatorTest {

    @Test
    fun calculatesSalaryForGroupOne() {
        val result = calculateSalary(BigDecimal("30000.00"), "Grupo 1")

        assertEquals(BigDecimal("30000.00"), result.grossSalary)
        assertEquals(BigDecimal("4500.00"), result.incomeTax)
        assertEquals(BigDecimal("300.00"), result.otherDeductions)
        assertEquals(BigDecimal("25200.00"), result.netSalary)
    }
}

