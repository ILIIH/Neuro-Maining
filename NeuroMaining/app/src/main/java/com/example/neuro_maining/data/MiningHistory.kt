package com.example.neuro_maining.data

import androidx.compose.ui.graphics.Color

data class MiningHistory(
    val miningResults: List<Pair<Int, Float>>,
    val color: Color,
    val miningSource: String
)

fun MiningHistory.getEarningSum() = this.miningResults.sumOf { it.second.toDouble() }
