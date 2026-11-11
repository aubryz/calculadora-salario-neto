package com.alvaropena.calculadorasalarioneto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.Role
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.alvaropena.calculadorasalarioneto.ui.theme.CalculadoraSalarioNetoTheme
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults

private val screenPadding = 16.dp
private val elementSpacing = 8.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraSalarioNetoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Calculadora de salario neto",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
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
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(screenPadding),
        verticalArrangement = Arrangement.spacedBy(elementSpacing)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = "Salario bruto anual")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = grossSalary.value,
            onValueChange = { newText ->
                grossSalary.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = grossSalary.value.isNotBlank() && !isGrossSalaryValid
        )
        Text(text = "Número de pagas")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberOfPayments.value,
            onValueChange = { newText ->
            numberOfPayments.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = numberOfPayments.value.isNotBlank() && !isPaymentCountValid
        )
        Text(text = "Edad")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = age.value,
            onValueChange = { newText ->
                age.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = age.value.isNotBlank() && !isAgeValid
        )
        Text(text = "Número de hijos")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberOfChildren.value,
            onValueChange = { newText ->
                numberOfChildren.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = numberOfChildren.value.isNotBlank() && !isChildCountValid
        )
        SingleChoiceDropdown(
            label = "Grupo profesional",
            options = professionalGroups,
            selectedOption = selectedProfessionalGroup.value,
            onOptionSelected = { option ->
                selectedProfessionalGroup.value = option
            }
        )
        Text(text = "Grado de discapacidad (%)")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = disabilityPercentage.value,
            onValueChange = { newText ->
                disabilityPercentage.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = disabilityPercentage.value.isNotBlank() && !isDisabilityValid
        )
        SingleChoiceDropdown(
            label = "Estado civil",
            options = maritalStatuses,
            selectedOption = selectedMaritalStatus.value,
            onOptionSelected = { option ->
                selectedMaritalStatus.value = option
            }
        )
        Button(
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
            Text(text = "Calcular")
        }
    }
}

@Composable
private fun SingleChoiceOptions(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(modifier = Modifier.selectableGroup()) {
        options.forEach { option ->
            val isSelected = selectedOption == option

            Row(
                modifier = Modifier.selectable(
                    selected = isSelected,
                    onClick = { onOptionSelected(option) },
                    role = Role.RadioButton
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = isSelected, onClick = null)
                Text(text = option)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SingleChoiceDropdown(
    label: String,
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
            onValueChange = {},
            readOnly = true,
            label = { Text(text = label) },
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

@Composable
private fun SalaryResultsScreen(
    result: SalaryResult,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val euroFormat = remember {
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-ES"))
    }
    BackHandler(onBack = onBack)

    Column(
        modifier = modifier.padding(screenPadding),
        verticalArrangement = Arrangement.spacedBy(elementSpacing)
    ) {
        Text(
            text = "Resultados del cálculo",
            style = MaterialTheme.typography.headlineSmall
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Salario neto anual")
                Text(
                    text = euroFormat.format(result.netSalary),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
        Text(text = "Salario bruto anual: ${euroFormat.format(result.grossSalary)}")
        Text(text = "Retención de IRPF: ${euroFormat.format(result.incomeTax)}")
        Text(text = "Otras deducciones: ${euroFormat.format(result.otherDeductions)}")

        Button(onClick = onBack) {
            Text(text = "Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraSalarioNetoTheme {
        Greeting("Calculadora de salario neto")
    }
}