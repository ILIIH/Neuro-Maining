package com.example.neuro_maining.data

import androidx.compose.ui.graphics.Color

data class MiningTask(
    val miningResults: List<Pair<Int, Float>>,
    val color: Color,
    val miningSource: String,
    val earningMultiplier: Float,
    val isClosed: Boolean,
    val closingDate: String? = null,
    val incomePerHour: Float
)

fun MiningTask.getEarningSum() = this.miningResults.sumOf { it.second.toDouble() }
