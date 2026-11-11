package com.alvaropena.calculadorasalarioneto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.LineBreak

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
            .padding(screenPadding)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    ),
                    color = Color.Transparent,
                    contentColor = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFF073B35), Color(0xFF0D665B))
                                )
                            )
                            .padding(start = 12.dp, top = 12.dp, end = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(elementSpacing)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic),
                                contentDescription = null,
                                modifier = Modifier.size(42.dp)
                            )

                            Text(
                                text = name,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    lineBreak = LineBreak.Heading
                                ),
                                fontFamily = CalculatorFont,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        CalculatorDisplay(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }

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
private val CalculatorFont = FontFamily(
    Font(R.font.bitcount_prop_single_medium, weight = FontWeight.Medium)
)
@Composable
private fun CalculatorDisplay(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(0.84f),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        color = Color(0xFF183F38),
        border = BorderStroke(
            width = 2.dp,
            brush = Brush.verticalGradient(
                0f to Color(0xFF729385),
                0.65f to Color(0xFF729385),
                1f to Color.Transparent
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                color = Color(0xFFDCE8C9),
                contentColor = Color(0xFF243923),
                border = BorderStroke(2.dp, Color(0xFF82997A))
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                0f to Color(0x2926352B),
                                0.25f to Color.Transparent
                            )
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(Color(0xFF9E540F), CircleShape)
                    )

                    Text(
                        text = "Actividad 1 para PDMD del gran CESAR CARRION",
                        modifier = Modifier
                            .weight(1f)
                            .basicMarquee(iterations = Int.MAX_VALUE),
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = CalculatorFont,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithCache {
                        val fade = Brush.verticalGradient(
                            0f to Color.Black,
                            0.2f to Color.Black,
                            1f to Color.Transparent
                        )

                        onDrawWithContent {
                            drawContent()
                            drawRect(brush = fade, blendMode = BlendMode.DstIn)
                        }
                    },
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf("7", "8", "9", "÷").forEach { key ->
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(20.dp),
                        shape = RoundedCornerShape(4.dp),
                        color = if (key == "÷") Color(0xFF927344) else Color(0xFF638378),
                        contentColor = Color.White
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = key,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
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