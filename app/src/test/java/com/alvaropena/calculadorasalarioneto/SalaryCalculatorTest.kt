package com.alvaropena.calculadorasalarioneto

import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Test

class SalaryCalculatorTest {

    @Test
    fun calculatesSalaryForGroupOne() {
        val result = calculateSalary(BigDecimal("30000.00"), ProfessionalGroup.GROUP_1)

        assertEquals(BigDecimal("30000.00"), result.grossSalary)
        assertEquals(BigDecimal("4500.00"), result.incomeTax)
        assertEquals(BigDecimal("300.00"), result.otherDeductions)
        assertEquals(BigDecimal("25200.00"), result.netSalary)
    }

    @Test
    fun calculatesSalaryForGroupTwo() {
        val result = calculateSalary(BigDecimal("30000.00"), ProfessionalGroup.GROUP_2)

        assertEquals(BigDecimal("600.00"), result.otherDeductions)
        assertEquals(BigDecimal("24900.00"), result.netSalary)
    }

    @Test
    fun calculatesSalaryForGroupThree() {
        val result = calculateSalary(BigDecimal("30000.00"), ProfessionalGroup.GROUP_3)

        assertEquals(BigDecimal("900.00"), result.otherDeductions)
        assertEquals(BigDecimal("24600.00"), result.netSalary)
    }
}

