package com.example.neuro_maining.domain

import com.example.neuro_maining.data.MiningTask
import com.example.neuro_maining.data.MiningTransaction
import com.example.neuro_maining.data.TransactionType
import com.example.neuro_maining.ui.theme.Graph1Color
import com.example.neuro_maining.ui.theme.Graph2Color

val miningTransactions = listOf<MiningTransaction>(
    MiningTransaction(
        id = "23FF2",
        amount = 23.2,
        currency = "£",
        type = TransactionType.DEPOSIT,
        date = "12 Jun 2026",
        taskName = "Intel devident"
    ),
    MiningTransaction(
        id = "45YY2",
        amount = 120.9,
        currency = "£",
        type = TransactionType.WITHDRAWAL,
        date = "11 Oct 2026",
        taskName = "SAP buy"
    )
)

val miningTasks = listOf(
    MiningTask(
        miningResults = listOf(
            3 to 200f,
            2 to 150f,
            12 to 100f,
            10 to 50f
        ),
        color = Graph1Color,
        miningSource = "Intel",
        earningMultiplier = 0.3f,
        isClosed = false,
        incomePerHour = 23.3f
    ),
    MiningTask(
        miningResults = listOf(
            3 to 230f,
            1 to 15f,
            14 to 100f,
            19 to 50f
        ),
        color = Graph2Color,
        miningSource = "SAP",
        earningMultiplier = 0.5f,
        isClosed = false,
        incomePerHour = 23.3f
    ),
    MiningTask(
        miningResults = listOf(
            3 to 230f,
            1 to 15f,
            14 to 100f,
            19 to 50f
        ),
        color = Graph2Color,
        miningSource = "SAP",
        earningMultiplier = 0.5f,
        isClosed = true,
        incomePerHour = 23.3f
    ),
    MiningTask(
        miningResults = listOf(
            3 to 230f,
            1 to 15f,
            14 to 100f,
            19 to 50f
        ),
        color = Graph2Color,
        miningSource = "Google",
        earningMultiplier = 0.9f,
        isClosed = true,
        incomePerHour = 23.3f
    )
)
