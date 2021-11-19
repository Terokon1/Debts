package com.chaev.debts.domain.models



data class Debt(
    val id: Int,
    val money: String,
    val creditor: String,
    val debtor: String,
    val created: String
)
