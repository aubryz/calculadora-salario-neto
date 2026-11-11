package com.alvaropena.calculadorasalarioneto.ui.salary

import com.alvaropena.calculadorasalarioneto.ProfessionalGroup
import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SalaryViewModelTest {
    @Test
    fun invalidDisabilityPreventsCalculation() {
        val viewModel = viewModelWithForm()
        viewModel.onDisabilityPercentageChanged("101")

        viewModel.onCalculate()

        val state = viewModel.uiState.value
        assertFalse(state.isDisabilityValid)
        assertFalse(state.canCalculate)
        assertNull(state.result)
    }

    @Test
    fun missingProfessionalGroupPreventsCalculation() {
        val viewModel = viewModelWithForm(professionalGroup = null)

        viewModel.onCalculate()

        val state = viewModel.uiState.value
        assertFalse(state.canCalculate)
        assertNull(state.result)
    }

    @Test
    fun validFormWithGroupedAmountProducesNetSalary() {
        val viewModel = viewModelWithForm()
        viewModel.onGrossSalaryChanged("30.000,50")
        assertTrue(viewModel.uiState.value.canCalculate)

        viewModel.onCalculate()

        val result = viewModel.uiState.value.result
        assertEquals(BigDecimal("30000.50"), result?.grossSalary)
        assertEquals(BigDecimal("25200.41"), result?.netSalary)
    }

    @Test
    fun returningFromResultsPreservesForm() {
        val viewModel = viewModelWithForm()
        val originalForm = viewModel.uiState.value
        viewModel.onCalculate()

        viewModel.onBackToForm()

        assertEquals(originalForm, viewModel.uiState.value)
    }

    private fun viewModelWithForm(
        professionalGroup: ProfessionalGroup? = ProfessionalGroup.GROUP_1
    ) = SalaryViewModel().apply {
        onGrossSalaryChanged("30000,00")
        onNumberOfPaymentsChanged("12")
        onAgeChanged("30")
        onNumberOfChildrenChanged("0")
        professionalGroup?.let(::onProfessionalGroupChanged)
        onDisabilityPercentageChanged("0")
        onMaritalStatusChanged(MaritalStatus.SINGLE)
    }
}
