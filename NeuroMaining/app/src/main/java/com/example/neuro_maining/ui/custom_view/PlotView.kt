package com.example.neuro_maining.ui.custom_view

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.sp
import java.util.Calendar

const val PLOT_MARGIN = 50f
const val AXIS_FONT_SIZE = 10

@Composable
fun PlotView(points: List<Pair<Float, Float>>) {
    val paint = Paint().apply {
        color = android.graphics.Color.BLACK
        style = Paint.Style.FILL
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth(1f)) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val originX = 0f

            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val hours = (0..currentHour).toList()
            val hourSpacing = canvasWidth / hours.size - PLOT_MARGIN/(hours.size+1)

            val axesPath = Path().apply {
                moveTo(PLOT_MARGIN, canvasHeight)
                lineTo(canvasWidth, canvasHeight)

                moveTo(originX + PLOT_MARGIN, PLOT_MARGIN)
                lineTo(originX + PLOT_MARGIN, canvasHeight)
            }

            val axesArrowPath = Path().apply {
                moveTo(PLOT_MARGIN, PLOT_MARGIN)
                lineTo(PLOT_MARGIN/2, 2*PLOT_MARGIN)
                moveTo(PLOT_MARGIN, PLOT_MARGIN)
                lineTo(PLOT_MARGIN+ PLOT_MARGIN/2, 2*PLOT_MARGIN)

                moveTo(canvasWidth, canvasHeight)
                lineTo(canvasWidth-PLOT_MARGIN, canvasHeight - PLOT_MARGIN/2)
                moveTo(canvasWidth, canvasHeight)
                lineTo(canvasWidth-PLOT_MARGIN, canvasHeight + PLOT_MARGIN/2)

            }

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

            val textPaint = Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = AXIS_FONT_SIZE.sp.toPx()
                textAlign = Paint.Align.CENTER
            }

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
                     10f,
                     paint
                )
            }
        }
    }
}