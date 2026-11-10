package com.alvaropena.calculadorasalarioneto

import android.os.Bundle
import androidx.activity.ComponentActivity
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
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text(text = name)
        Text(text = "Salario bruto anual")
        OutlinedTextField(
            value = grossSalary.value,
            onValueChange = { newText ->
                grossSalary.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Text(text = "Número de pagas")
        OutlinedTextField(
            value = numberOfPayments.value,
            onValueChange = { newText ->
            numberOfPayments.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = "Edad")
        OutlinedTextField(
            value = age.value,
            onValueChange = { newText ->
                age.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = "Número de hijos")
        OutlinedTextField(
            value = numberOfChildren.value,
            onValueChange = { newText ->
                numberOfChildren.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = "Grupo profesional")
        SingleChoiceOptions(
            options = professionalGroups,
            selectedOption = selectedProfessionalGroup.value,
            onOptionSelected = { option ->
                selectedProfessionalGroup.value = option
            }
        )
        Text(text = "Grado de discapacidad (%)")
        OutlinedTextField(
            value = disabilityPercentage.value,
            onValueChange = { newText ->
                disabilityPercentage.value = newText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = "Estado civil")
        SingleChoiceOptions(
            options = maritalStatuses,
            selectedOption = selectedMaritalStatus.value,
            onOptionSelected = { option ->
                selectedMaritalStatus.value = option
            }
        )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraSalarioNetoTheme {
        Greeting("Calculadora de salario neto")
    }
}