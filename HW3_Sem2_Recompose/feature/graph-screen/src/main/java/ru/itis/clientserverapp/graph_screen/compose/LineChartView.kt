package ru.itis.clientserverapp.graph_screen.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun LineChartView(values: List<Float>) {
    if (values.isEmpty()) return

    val maxValue = values.maxOrNull() ?: 0f
    val minValue = values.minOrNull() ?: 0f
    val yRange = (maxValue - minValue).takeIf { it != 0f } ?: 1f

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawLine(
            color = Color.Gray,
            start = Offset(0f, canvasHeight),
            end = Offset(canvasWidth, canvasHeight),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.Gray,
            start = Offset(0f, 0f),
            end = Offset(0f, canvasHeight),
            strokeWidth = 2f
        )

        val pointCount = values.size
        val spacing = canvasWidth / (pointCount - 1).coerceAtLeast(1)

        val points = values.mapIndexed { index, yValue ->
            val x = index * spacing
            val y = canvasHeight - ((yValue - minValue) / yRange * canvasHeight)
            Offset(x, y)
        }

        val path = Path().apply {
            moveTo(points.first().x, canvasHeight)
            points.forEach { lineTo(it.x, it.y) }
            lineTo(points.last().x, canvasHeight)
            close()
        }

        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(Color.Blue.copy(alpha = 0.4f), Color.Transparent)
            )
        )

        for (i in 0 until points.size - 1) {
            drawLine(
                color = Color.Blue,
                start = points[i],
                end = points[i + 1],
                strokeWidth = 4f
            )
        }

        points.forEach { point ->
            drawCircle(
                color = Color.Blue,
                radius = 6f,
                center = point
            )
        }
    }
}
