package com.example.neuro_maining.ui.custom_view

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import java.util.Calendar
import kotlin.random.Random

const val PLOT_MARGIN = 50f
const val AXIS_FONT_SIZE = 30f
const val PLOT_WIDTH_FACTOR = 1.1f

@Composable
fun PlotView(points:List<List<Pair<Int, Float>>>, modifier: Modifier = Modifier) {

    val axesPath = remember { Path() }
    val axesArrowPath = remember { Path() }

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
        axesPath.reset()
        axesArrowPath.reset()

        axesPath.apply {
            moveTo(PLOT_MARGIN, canvasHeight)
            lineTo(canvasWidth/PLOT_WIDTH_FACTOR, canvasHeight)

            moveTo(PLOT_MARGIN, 0f)
            lineTo(PLOT_MARGIN, canvasHeight)
        }

        axesArrowPath.apply {
            moveTo(PLOT_MARGIN, 0f)
            lineTo(PLOT_MARGIN / 2,  PLOT_MARGIN)
            moveTo(PLOT_MARGIN, 0f)
            lineTo(PLOT_MARGIN + PLOT_MARGIN / 2,   PLOT_MARGIN)

            moveTo(canvasWidth/PLOT_WIDTH_FACTOR, canvasHeight)
            lineTo(canvasWidth/PLOT_WIDTH_FACTOR - PLOT_MARGIN/2, canvasHeight - PLOT_MARGIN / 2)
            moveTo(canvasWidth/PLOT_WIDTH_FACTOR, canvasHeight)
            lineTo(canvasWidth/PLOT_WIDTH_FACTOR - PLOT_MARGIN/2, canvasHeight + PLOT_MARGIN / 2)
        }
    }

    fun drawPoints(canvasHeight: Float, canvasWidth: Float, drawContext:DrawContext){
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hours = (0..currentHour).toList()
        val hourSpacing = (canvasWidth / hours.size - PLOT_MARGIN / (hours.size + 1))/PLOT_WIDTH_FACTOR

        var maxValue = 0f
        for(poinsPool in points){
            val currentMax = poinsPool.maxBy { it.second}.second
            maxValue = if(currentMax > maxValue ) currentMax+PLOT_MARGIN else maxValue
        }

        for(poinsPool in points){
            val earningPath = Path()
            earningPath.moveTo(PLOT_MARGIN, canvasHeight)

            hours.forEachIndexed { index, hour ->
                val x = hourSpacing * index
                val n = poinsPool.find { it.first == index}
                if(n!=null){
                    val y = (  canvasHeight * n.second - canvasHeight) / maxValue
                    earningPath.apply {
                        lineTo(x+ PLOT_MARGIN, y)
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
                    color = Color(
                        red = Random.nextFloat(),
                        green = Random.nextFloat(),
                        blue = Random.nextFloat(),
                        alpha = 1.0f
                    )
                    strokeWidth = 10.0f
                    style = PaintingStyle.Stroke
                }
            )
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
                6f,
                paint
            )
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
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