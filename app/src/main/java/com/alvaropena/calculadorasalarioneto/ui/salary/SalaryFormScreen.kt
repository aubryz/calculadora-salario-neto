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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvaropena.calculadorasalarioneto.ProfessionalGroup
import com.alvaropena.calculadorasalarioneto.R
import com.alvaropena.calculadorasalarioneto.ui.salary.components.SalaryHeader
import com.alvaropena.calculadorasalarioneto.ui.theme.CalculadoraSalarioNetoTheme

private val screenPadding = 16.dp
private val elementSpacing = 8.dp
private val formContentColor = Color(0xFFF0F7F3)

@Composable
internal fun SalaryFormScreen(
    uiState: SalaryUiState,
    onGrossSalaryChanged: (String) -> Unit,
    onNumberOfPaymentsChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    onNumberOfChildrenChanged: (String) -> Unit,
    onProfessionalGroupChanged: (ProfessionalGroup) -> Unit,
    onDisabilityPercentageChanged: (String) -> Unit,
    onMaritalStatusChanged: (String) -> Unit,
    onCalculate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val showGrossSalaryError = uiState.grossSalary.isNotBlank() && !uiState.isGrossSalaryValid
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
            contentColor = formContentColor,
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
                        value = uiState.grossSalary,
                        onValueChange = onGrossSalaryChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = showGrossSalaryError,
                        supportingText = if (showGrossSalaryError) {
                            {
                                Text(
                                    text = stringResource(R.string.salary_gross_amount_error),
                                    color = formContentColor
                                )
                            }
                        } else {
                            null
                        },
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small,
                    )

                    Text(text = "Número de pagas")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.numberOfPayments,
                        onValueChange = onNumberOfPaymentsChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.numberOfPayments.isNotBlank() && !uiState.isPaymentCountValid,
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small,
                    )

                    Text(text = "Edad")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.age,
                        onValueChange = onAgeChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.age.isNotBlank() && !uiState.isAgeValid,
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small,
                    )

                    Text(text = "Número de hijos")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.numberOfChildren,
                        onValueChange = onNumberOfChildrenChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.numberOfChildren.isNotBlank() && !uiState.isChildCountValid,
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small
                    )

                    Text(text = "Grupo profesional")
                    SingleChoiceDropdown(
                        options = ProfessionalGroup.entries,
                        selectedOption = uiState.selectedProfessionalGroup,
                        optionLabel = ProfessionalGroup::displayLabel,
                        colors = fieldColors,
                        onOptionSelected = onProfessionalGroupChanged
                    )

                    Text(text = "Grado de discapacidad (%)")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.disabilityPercentage,
                        onValueChange = onDisabilityPercentageChanged,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.disabilityPercentage.isNotBlank() && !uiState.isDisabilityValid,
                        colors = fieldColors,
                        shape = MaterialTheme.shapes.small
                    )

                    Text(text = "Estado civil")
                    SingleChoiceDropdown(
                        options = SalaryUiState.maritalStatuses,
                        selectedOption = uiState.selectedMaritalStatus,
                        optionLabel = { it },
                        colors = fieldColors,
                        onOptionSelected = onMaritalStatusChanged
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
                        onClick = onCalculate,
                        enabled = uiState.canCalculate
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

private fun ProfessionalGroup.displayLabel(): String = when (this) {
    ProfessionalGroup.GROUP_1 -> "Grupo 1"
    ProfessionalGroup.GROUP_2 -> "Grupo 2"
    ProfessionalGroup.GROUP_3 -> "Grupo 3"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T : Any> SingleChoiceDropdown(
    colors: TextFieldColors,
    options: List<T>,
    selectedOption: T?,
    optionLabel: (T) -> String,
    onOptionSelected: (T) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        OutlinedTextField(
            value = selectedOption?.let(optionLabel).orEmpty(),
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
                    text = { Text(text = optionLabel(option)) },
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
private fun SalaryFormScreenPreview() {
    CalculadoraSalarioNetoTheme(darkTheme = false, dynamicColor = false) {
        SalaryFormScreen(
            uiState = SalaryUiState(),
            onGrossSalaryChanged = {},
            onNumberOfPaymentsChanged = {},
            onAgeChanged = {},
            onNumberOfChildrenChanged = {},
            onProfessionalGroupChanged = {},
            onDisabilityPercentageChanged = {},
            onMaritalStatusChanged = {},
            onCalculate = {}
        )
    }
}
