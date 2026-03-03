package com.simats.gokart.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.simats.gokart.data.DataPoint

@Composable
fun LineChart(
    data: List<DataPoint>,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val gridColor = Color.LightGray.copy(alpha = 0.5f)
        val dashPathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

        // Draw horizontal grid lines
        val gridLines = 4
        for (i in 0..gridLines) {
            val y = size.height * i / gridLines
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                pathEffect = dashPathEffect,
                strokeWidth = 1.dp.toPx()
            )
        }

        if (data.size < 2) return@Canvas

        val min = data.minOf { it.value }
        val max = data.maxOf { it.value }

        // If all values are the same, center the line
        val range = if (max - min == 0f) 1f else max - min
        val centeredY = if (max - min == 0f) size.height / 2 else 0f

        val path = Path()
        data.forEachIndexed { i, dataPoint ->
            val x = i.toFloat() / (data.size - 1) * size.width
            val y = if (max - min == 0f) centeredY else (1 - (dataPoint.value - min) / range) * size.height
            if (i == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        // Draw the line
        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = 3.dp.toPx(),
                pathEffect = PathEffect.cornerPathEffect(50f) // Smooth corners
            )
        )

        // Draw fill area
        val fillPath = Path().apply {
            addPath(path)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(color.copy(alpha = 0.3f), Color.Transparent)
            )
        )
    }
}
