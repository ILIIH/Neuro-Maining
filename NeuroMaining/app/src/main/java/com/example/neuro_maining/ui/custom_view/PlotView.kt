package com.example.neuro_maining.ui.custom_view

import android.graphics.Paint
import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import com.example.neuro_maining.data.MiningHistory
import java.util.Calendar

const val PLOT_MARGIN = 50f
const val AXIS_FONT_SIZE = 25f
const val PLOT_WIDTH_FACTOR = 1.1f

@Composable
fun PlotView(points:List<MiningHistory>, modifier: Modifier = Modifier) {

    val axesPath = remember { Path() }

    val textPaint = remember{Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = AXIS_FONT_SIZE
        textAlign = Paint.Align.CENTER
    }}

    val axesPaint = Paint().apply {
        color = android.graphics.Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }

    val graphPaint = Paint().apply {
        color = android.graphics.Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 8f
    }
    fun configurePaths(canvasWidth: Float, canvasHeight: Float) {
        axesPath.reset()
        axesPath.apply {
            moveTo(PLOT_MARGIN, canvasHeight)
            lineTo(canvasWidth/PLOT_WIDTH_FACTOR, canvasHeight)

            moveTo(PLOT_MARGIN, 0f)
            lineTo(PLOT_MARGIN, canvasHeight)
        }
    }

    fun calculateY(maxValue: Float, canvasHeight: Float, realY : Float) = ( maxValue *PLOT_MARGIN)/canvasHeight+ canvasHeight -  (canvasHeight *  realY )/maxValue

    fun drawGraph(canvasHeight: Float, hours: List<Int>, hourSpacing: Float, maxValue:Float, drawContext:DrawContext){
        for(poinsPool in points){
            val earningPath = Path()
            earningPath.moveTo(PLOT_MARGIN, canvasHeight)

            hours.forEachIndexed { index, hour ->
                val x = hourSpacing * index
                val n = poinsPool.miningResults.find { it.first == index}
                if(n!=null){
                    val y = calculateY(maxValue = maxValue, canvasHeight = canvasHeight, realY = n.second)
                    earningPath.apply {
                        lineTo(x+ PLOT_MARGIN, y)
                    }
                }
            }

            drawContext.canvas.nativeCanvas.drawPath(
                earningPath,
                Paint().apply {
                    color = poinsPool.color.toArgb()
                    strokeWidth = 10.0f
                    style = Paint.Style.STROKE
                }
            )

        }
    }

    fun drawPoints(canvasHeight: Float, hours: List<Int>, hourSpacing: Float, maxValue:Float, drawContext:DrawContext){
        for(poinsPool in points) {
            hours.forEachIndexed { index, hour ->
                val x = hourSpacing * index
                val n = poinsPool.miningResults.find { it.first == index}
                if(n!=null){
                    val y = calculateY(maxValue = maxValue, canvasHeight = canvasHeight, realY = n.second)
                    drawContext.canvas.nativeCanvas.drawCircle(
                        x + PLOT_MARGIN,
                        y,
                        14f,
                        graphPaint.apply {
                            color = poinsPool.color.toArgb()
                        }
                    )
                    drawContext.canvas.nativeCanvas.drawCircle(
                        x + PLOT_MARGIN,
                        y,
                        8f,
                        graphPaint.apply {
                            color = Color.White.toArgb()
                            style = Paint.Style.FILL
                        }
                    )
                }
            }

        }
    }

    fun drawCoordinateY(canvasHeight: Float,maxValue:Float,drawContext:DrawContext){
        val earningStep = (maxValue * 0.04 * (canvasHeight / maxValue)).toInt()
        var tempMaxValue = maxValue

        for (i in PLOT_MARGIN.toInt() until canvasHeight.toInt() step earningStep) {
            val y = calculateY(maxValue, canvasHeight, tempMaxValue)

            drawContext.canvas.nativeCanvas.apply {
                if (tempMaxValue >= 0) {
                    drawText("${tempMaxValue.toInt()}", 0f, y, textPaint)
                }
                drawLine(
                    PLOT_MARGIN - PLOT_MARGIN / 4,
                    i.toFloat(),
                    PLOT_MARGIN + PLOT_MARGIN / 4,
                    i.toFloat(),
                    axesPaint
                )
            }
            tempMaxValue -= earningStep
        }
    }
    fun drawCoordinateX(canvasHeight: Float,hourSpacing: Float,hours: List<Int>,drawContext:DrawContext){
        hours.forEachIndexed { index, hour ->
            val x = hourSpacing * index
            drawContext.canvas.nativeCanvas.drawText(
                "$hour",
                x + PLOT_MARGIN,
                canvasHeight + PLOT_MARGIN,
                textPaint
            )

            drawContext.canvas.nativeCanvas.drawLine(
                x + PLOT_MARGIN,
                canvasHeight-PLOT_MARGIN/4,
                x + PLOT_MARGIN,
                canvasHeight+PLOT_MARGIN/4,
                axesPaint
            )
        }
    }
    fun onDraw(canvasHeight: Float, canvasWidth: Float, drawContext:DrawContext){
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hours = (0..currentHour).toList()
        val hourSpacing = (canvasWidth / hours.size - PLOT_MARGIN / (hours.size + 1))/PLOT_WIDTH_FACTOR

        var maxValue = 0f
        for(poinsPool in points){
            val currentMax = poinsPool.miningResults.maxBy { it.second}.second
            maxValue = if(currentMax > maxValue ) currentMax+PLOT_MARGIN else maxValue
        }

        drawCoordinateX(
            canvasHeight = canvasHeight,
            hours = hours,
            hourSpacing = hourSpacing,
            drawContext = drawContext
        )

        drawCoordinateY(
            canvasHeight= canvasHeight,
            maxValue = maxValue,
            drawContext = drawContext)

         drawGraph(
             canvasHeight = canvasHeight,
             hours = hours,
             hourSpacing = hourSpacing,
             maxValue = maxValue,
             drawContext = drawContext
            )

        drawPoints(
            canvasHeight = canvasHeight,
            hours = hours,
            hourSpacing = hourSpacing,
            maxValue = maxValue,
            drawContext = drawContext
        )

    }


    Canvas(modifier = modifier) {
        configurePaths(size.width, size.height)
        drawContext.canvas.nativeCanvas.drawPath(axesPath , axesPaint)
        onDraw(size.height, size.width, drawContext)
    }
}