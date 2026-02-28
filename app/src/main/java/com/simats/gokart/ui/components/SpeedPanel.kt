package com.simats.gokart.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.gokart.data.DataPoint
import com.simats.gokart.ui.theme.AccentAmber
import com.simats.gokart.ui.theme.BgCharcoal
import com.simats.gokart.ui.theme.BgPanel
import com.simats.gokart.ui.theme.BorderColor
import com.simats.gokart.ui.theme.TextSecondary

@Composable
fun SpeedPanel(speed: Int, history: List<DataPoint>) {
    val maxSpeed = 85f
    val barWidth = (speed / maxSpeed).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Taller to accommodate value
            .background(BgPanel)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SPEED",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "KM/H",
                color = TextSecondary,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Large Number
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = speed.toString(),
                color = AccentAmber,
                fontSize = 72.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-2).sp
            )
        }

        // Horizontal Bar
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("0", "25", "50", "75", "85").forEach {
                    Text(text = it, color = TextSecondary, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(BgCharcoal)
                    .border(1.dp, BorderColor)
            ) {
                // Ticks (conceptual)
                // Fill
                Box(
                    modifier = Modifier
                        .fillMaxWidth(barWidth)
                        .fillMaxHeight()
                        .background(AccentAmber.copy(alpha = 0.9f))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sparkline
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .alpha(0.6f)
        ) {
            if (history.isEmpty()) return@Canvas

            val width = size.width
            val height = size.height
            val path = Path()
            
            val maxVal = 100f // Domain max
            val points = history.takeLast(40) // Limit points
            
            if (points.isNotEmpty()) {
                val stepX = width / (points.size - 1).coerceAtLeast(1)
                
                points.forEachIndexed { index, point ->
                    val x = index * stepX
                    val y = height - (point.value / maxVal * height)
                    if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                
                // Stroke
                drawPath(
                    path = path,
                    color = AccentAmber,
                    style = Stroke(width = 3f)
                )
                
                // Fill gradient
                path.lineTo(width, height)
                path.lineTo(0f, height)
                path.close()
                
                drawPath(
                    path = path,
                    brush = Brush.verticalGradient(
                        colors = listOf(AccentAmber.copy(alpha = 0.3f), Color.Transparent)
                    )
                )
            }
        }
    }
}
