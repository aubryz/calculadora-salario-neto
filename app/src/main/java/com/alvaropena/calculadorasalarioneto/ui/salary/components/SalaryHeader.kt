package com.alvaropena.calculadorasalarioneto.ui.salary.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alvaropena.calculadorasalarioneto.R

private val CalculatorFont = FontFamily(
    Font(R.font.bitcount_prop_single_medium, weight = FontWeight.Medium)
)

@Composable
internal fun SalaryHeader(title: String, showDisplay: Boolean) {
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
                .padding(
                    start = 12.dp,
                    top = 12.dp,
                    end = 12.dp,
                    bottom = if (showDisplay) 0.dp else 12.dp
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        lineBreak = LineBreak.Heading
                    ),
                    fontFamily = CalculatorFont,
                    fontWeight = FontWeight.Medium
                )
            }

            if (showDisplay) {
                CalculatorDisplay(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

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
