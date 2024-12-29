package com.example.neuro_maining.ui.custom_view

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import kotlinx.coroutines.yield
import java.util.Calendar

const val PLOT_MARGIN = 50f
const val AXIS_FONT_SIZE = 30f

@Composable
fun PlotView(points: List<Pair<Int, Float>>) {

    val axesPath = remember { Path() }
    val axesArrowPath = remember { Path() }
    val earningPath = remember { Path() }

    val textPaint = remember{Paint().apply {
        color = android.graphics.Color.BLACK
        textSize = AXIS_FONT_SIZE
        textAlign = Paint.Align.CENTER
    }}

    val paint = Paint().apply {
        color = android.graphics.Color.BLACK
        style = Paint.Style.FILL
    }

    fun configurePaths(canvasWidth: Float, canvasHeight: Float) {
        // Clear previous paths before configuring new ones
        axesPath.reset()
        axesArrowPath.reset()

        // Axis path: X-axis and Y-axis lines
        axesPath.apply {
            moveTo(PLOT_MARGIN, canvasHeight)
            lineTo(canvasWidth, canvasHeight)

            moveTo(PLOT_MARGIN, PLOT_MARGIN)
            lineTo(PLOT_MARGIN, canvasHeight)
        }

        // Arrow path for axis markers
        axesArrowPath.apply {
            // Arrow for Y-axis
            moveTo(PLOT_MARGIN, PLOT_MARGIN)
            lineTo(PLOT_MARGIN / 2, 2 * PLOT_MARGIN)
            moveTo(PLOT_MARGIN, PLOT_MARGIN)
            lineTo(PLOT_MARGIN + PLOT_MARGIN / 2, 2 * PLOT_MARGIN)

            // Arrow for X-axis
            moveTo(canvasWidth, canvasHeight)
            lineTo(canvasWidth - PLOT_MARGIN, canvasHeight - PLOT_MARGIN / 2)
            moveTo(canvasWidth, canvasHeight)
            lineTo(canvasWidth - PLOT_MARGIN, canvasHeight + PLOT_MARGIN / 2)
        }
    }

    fun drawPoints(canvasHeight: Float, canvasWidth: Float, drawContext:DrawContext){
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hours = (0..currentHour).toList()
        val hourSpacing = canvasWidth / hours.size - PLOT_MARGIN / (hours.size + 1)

        earningPath.moveTo(PLOT_MARGIN, canvasHeight)
        val maxValue = points.maxBy { it.second}
        Log.i("Drawing_logging","points = ${points}")

        hours.forEachIndexed { index, hour ->
            val x = hourSpacing * index
            drawContext.canvas.nativeCanvas.drawText(
                "$hour",
                x + PLOT_MARGIN,
                canvasHeight + PLOT_MARGIN,
                textPaint
            )

            drawContext.canvas.nativeCanvas.drawCircle(
                x + PLOT_MARGIN,
                canvasHeight,
                6f,
                paint
            )
            Log.i("Drawing_logging","x = ${x}")
            val n = points.find { it.first == index}
            if(n!=null){
                val y = (  canvasHeight * n.second - canvasHeight) / maxValue.second
                earningPath.apply {
                    lineTo(x, y)
                    drawContext.canvas.nativeCanvas.drawCircle(
                        x + PLOT_MARGIN,
                        y,
                        14f,
                        paint
                    )
                }
            }

        }

        drawContext.canvas.drawPath(
            path = earningPath,
            androidx.compose.ui.graphics.Paint().apply {
                color = Color.Black
            strokeWidth = 12.0f}
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxHeight(0.3f).fillMaxWidth(1f)) {
            configurePaths(size.width, size.height)

            drawPath(
                path = axesArrowPath,
                color = Color.Black,
                style = Stroke(width = 2f)
            )

            drawPath(
                path = axesPath,
                color = Color.Black,
                style = Stroke(width = 2f)
            )
            drawPoints(size.height, size.width, drawContext)
        }
    }
}