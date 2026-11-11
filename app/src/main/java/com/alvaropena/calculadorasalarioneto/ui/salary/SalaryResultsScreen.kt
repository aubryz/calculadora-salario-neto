package com.alvaropena.calculadorasalarioneto.ui.salary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvaropena.calculadorasalarioneto.R
import com.alvaropena.calculadorasalarioneto.SalaryResult
import com.alvaropena.calculadorasalarioneto.ui.salary.components.SalaryHeader
import com.alvaropena.calculadorasalarioneto.ui.theme.CalculadoraSalarioNetoTheme
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale
/**
 * Presenta el salario neto anual y el desglose del cálculo.
 * Delega la vuelta al formulario en la acción que recibe.
 */
@Composable
internal fun SalaryResultsScreen(
    result: SalaryResult,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Mantiene el formato monetario español aunque el dispositivo use otra región.
    val euroFormat = remember {
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-ES"))
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SalaryHeader(title = stringResource(R.string.salary_results_title), showDisplay = false)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(R.string.salary_net_annual))
                Text(
                    text = euroFormat.format(result.netSalary),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            val breakdown = listOf(
                R.string.salary_gross_annual to result.grossSalary,
                R.string.salary_income_tax to result.incomeTax,
                R.string.salary_other_deductions to result.otherDeductions
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                breakdown.forEach { (labelRes, amount) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = stringResource(labelRes),
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = euroFormat.format(amount),
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }

        Button(onClick = onBack) {
            Text(text = stringResource(R.string.salary_back))
        }
    }
}

@Preview(name = "Resultados del salario", showBackground = true)
@Composable
private fun SalaryResultsScreenPreview() {
    CalculadoraSalarioNetoTheme(darkTheme = false, dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            SalaryResultsScreen(
                result = SalaryResult(
                    grossSalary = BigDecimal("30000.50"),
                    incomeTax = BigDecimal("4500.08"),
                    otherDeductions = BigDecimal("300.01"),
                    netSalary = BigDecimal("25200.41")
                ),
                onBack = {}
            )
        }
    }
}
