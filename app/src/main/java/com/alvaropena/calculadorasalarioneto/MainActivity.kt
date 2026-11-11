package com.alvaropena.calculadorasalarioneto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.alvaropena.calculadorasalarioneto.ui.salary.SalaryCalculatorScreen
import com.alvaropena.calculadorasalarioneto.ui.theme.CalculadoraSalarioNetoTheme
/**
 * Punto de entrada de la aplicación. Configura la ventana y carga
 * la calculadora con el tema de la app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraSalarioNetoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Respeta el espacio de las barras del sistema al dibujar de borde a borde.
                    SalaryCalculatorScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
