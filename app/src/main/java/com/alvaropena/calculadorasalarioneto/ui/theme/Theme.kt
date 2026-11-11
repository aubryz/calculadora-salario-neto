package com.alvaropena.calculadorasalarioneto.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = TealPrimaryDark,
    onPrimary = TealOnPrimaryDark,
    primaryContainer = TealContainerDark,
    onPrimaryContainer = TealOnContainerDark,
    background = TealSurfaceDark,
    onBackground = TealOnSurfaceDark,
    surface = TealSurfaceDark,
    onSurface = TealOnSurfaceDark,
    surfaceContainerLowest = Color(0xFF0E211D)
)

private val LightColorScheme = lightColorScheme(
    primary = TealPrimaryLight,
    onPrimary = TealOnPrimaryLight,
    primaryContainer = TealContainerLight,
    onPrimaryContainer = TealOnContainerLight,
    background = TealSurfaceLight,
    onBackground = TealOnSurfaceLight,
    surface = TealSurfaceLight,
    onSurface = TealOnSurfaceLight,
    surfaceContainerLow = Color(0xFF0D665B),
    surfaceContainerLowest = Color.White
)
/**
 * Aplica la paleta de colores y la tipografía a la interfaz de la calculadora.
 * Por defecto, usa la paleta propia de la app y sigue el modo claro u oscuro del sistema.
 */
@Composable
fun CalculadoraSalarioNetoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Conserva la paleta de la app por defecto. Los colores dinámicos
    // se pueden activar a partir de Android 12.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}