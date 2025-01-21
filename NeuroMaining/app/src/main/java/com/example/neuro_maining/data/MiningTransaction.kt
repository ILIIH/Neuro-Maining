package com.example.neuro_maining.data

enum class TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    PENDING,
}
data class MiningTransaction(
    val id: String,
    val amount: Double,
    val currency: String,
    val type: TransactionType,
    val date: String,
    val taskName: String,
    val description: String? = null
)
{
    override fun toString() = """ ${if (type == TransactionType.DEPOSIT) "+" else "-"} $currency $amount"""
}
