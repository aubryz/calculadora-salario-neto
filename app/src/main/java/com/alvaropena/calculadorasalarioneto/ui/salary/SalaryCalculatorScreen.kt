package com.alvaropena.calculadorasalarioneto.ui.salary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvaropena.calculadorasalarioneto.R
import com.alvaropena.calculadorasalarioneto.SalaryResult
import com.alvaropena.calculadorasalarioneto.calculateSalary
import com.alvaropena.calculadorasalarioneto.ui.salary.components.SalaryHeader
import com.alvaropena.calculadorasalarioneto.ui.theme.CalculadoraSalarioNetoTheme

private val screenPadding = 16.dp
private val elementSpacing = 8.dp

@Composable
internal fun SalaryCalculatorScreen(modifier: Modifier = Modifier) {
    val grossSalary = remember { mutableStateOf("") }
    val numberOfPayments = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val numberOfChildren = remember { mutableStateOf("") }
    val professionalGroups = listOf("Grupo 1", "Grupo 2", "Grupo 3")
    val selectedProfessionalGroup = remember { mutableStateOf("") }
    val disabilityPercentage = remember { mutableStateOf("") }
    val maritalStatuses = listOf("Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a")
    val selectedMaritalStatus = remember { mutableStateOf("") }

    val grossSalaryAmount = grossSalary.value
        .trim()
        .replace(',', '.')
        .toBigDecimalOrNull()
    val isGrossSalaryValid = grossSalaryAmount != null && grossSalaryAmount.signum() > 0

    val paymentCount = numberOfPayments.value.trim().toIntOrNull()
    val isPaymentCountValid = paymentCount != null && paymentCount > 0

    val ageYears = age.value.trim().toIntOrNull()
    val isAgeValid = ageYears != null && ageYears > 0

    val childCount = numberOfChildren.value.trim().toIntOrNull()
    val isChildCountValid = childCount != null && childCount >= 0

    val disabilityValue = disabilityPercentage.value.trim().toIntOrNull()
    val isDisabilityValid = disabilityValue != null && disabilityValue in 0..100

    val canCalculate = isGrossSalaryValid &&
            isPaymentCountValid &&
            isAgeValid &&
            isChildCountValid &&
            isDisabilityValid &&
            selectedProfessionalGroup.value in professionalGroups &&
            selectedMaritalStatus.value in maritalStatuses

    val salaryResult = remember { mutableStateOf<SalaryResult?>(null) }
    val result = salaryResult.value

    if (result != null) {
        SalaryResultsScreen(
            result = result,
            onBack = { salaryResult.value = null },
            modifier = modifier
        )
        return
    }
    val fieldBackground = MaterialTheme.colorScheme.surfaceContainerLowest
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = fieldBackground,
        unfocusedContainerColor = fieldBackground,
        errorContainerColor = fieldBackground
    )

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(screenPadding)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = Color(0xFFF0F7F3),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column {
                SalaryHeader(title = "Calculadora de salario neto", showDisplay = true)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(elementSpacing)
                ) {
                    Text(text = "Salario bruto anual")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = grossSalary.value,
                        onValueChange = { newText ->
                            grossSalary.value = newText
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = grossSalary.value.isNotBlank() && !isGrossSalaryValid,
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small,
                    )

                    Text(text = "Número de pagas")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = numberOfPayments.value,
                        onValueChange = { newText ->
                            numberOfPayments.value = newText
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = numberOfPayments.value.isNotBlank() && !isPaymentCountValid,
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small,
                    )

                    Text(text = "Edad")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = age.value,
                        onValueChange = { newText ->
                            age.value = newText
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = age.value.isNotBlank() && !isAgeValid,
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small,
                    )

                    Text(text = "Número de hijos")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = numberOfChildren.value,
                        onValueChange = { newText ->
                            numberOfChildren.value = newText
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = numberOfChildren.value.isNotBlank() && !isChildCountValid,
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small
                    )

                    Text(text = "Grupo profesional")
                    SingleChoiceDropdown(
                        options = professionalGroups,
                        selectedOption = selectedProfessionalGroup.value,
                        colors = fieldColors,
                        onOptionSelected = { selectedProfessionalGroup.value = it }
                    )

                    Text(text = "Grado de discapacidad (%)")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = disabilityPercentage.value,
                        onValueChange = { newText ->
                            disabilityPercentage.value = newText
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = disabilityPercentage.value.isNotBlank() && !isDisabilityValid,
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small
                    )

                    Text(text = "Estado civil")
                    SingleChoiceDropdown(
                        options = maritalStatuses,
                        selectedOption = selectedMaritalStatus.value,
                        colors = fieldColors,
                        onOptionSelected = { selectedMaritalStatus.value = it }
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .heightIn(min = 48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF3BD72),
                            contentColor = Color(0xFF073B35),
                            disabledContainerColor = Color(0xFF084C44),
                            disabledContentColor = Color(0xFFB8D4CB)
                        ),
                        onClick = {
                            if (grossSalaryAmount != null) {
                                salaryResult.value = calculateSalary(
                                    grossSalary = grossSalaryAmount,
                                    professionalGroup = selectedProfessionalGroup.value
                                )
                            }
                        },
                        enabled = canCalculate
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Calcular salario neto",
                                modifier = Modifier.weight(1f)
                            )

                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_forward),
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SingleChoiceDropdown(
    colors: TextFieldColors,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        OutlinedTextField(
            value = selectedOption,
            placeholder = { Text("Elige una opción") },
            colors = colors,
            shape = MaterialTheme.shapes.small,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded.value = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SalaryCalculatorScreenPreview() {
    CalculadoraSalarioNetoTheme(darkTheme = false, dynamicColor = false) {
        SalaryCalculatorScreen()
    }
}
