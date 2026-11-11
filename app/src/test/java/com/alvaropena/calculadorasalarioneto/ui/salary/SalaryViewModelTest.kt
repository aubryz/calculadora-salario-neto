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
    fun validFormWithDecimalCommaProducesNetSalary() {
        val viewModel = viewModelWithForm()
        assertTrue(viewModel.uiState.value.canCalculate)

        viewModel.onCalculate()

        assertEquals(BigDecimal("25200.00"), viewModel.uiState.value.result?.netSalary)
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
        onMaritalStatusChanged("Soltero/a")
    }
}
