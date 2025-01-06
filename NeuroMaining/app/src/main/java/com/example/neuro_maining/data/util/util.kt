package com.example.neuro_maining.data.util

import com.example.neuro_maining.data.MiningHistory

fun MiningHistory.getEarningSum() = this.miningResults.sumOf { it.second.toDouble() }