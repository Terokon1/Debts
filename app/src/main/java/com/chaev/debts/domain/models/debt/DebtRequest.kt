package com.chaev.debts.domain.models.debt

import com.chaev.debts.domain.models.base.User

data class DebtRequest(
    val id: String,
    val money: String,
    val creditor: User,
    val debtor: User,
    val isYours: Boolean,
    val description: String,
    val created: String,
    val isActive: Boolean
)
