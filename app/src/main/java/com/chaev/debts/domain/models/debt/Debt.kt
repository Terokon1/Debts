package com.chaev.debts.domain.models.debt

import com.chaev.debts.domain.models.base.User

data class Debt(
    val id: String,
    val money: String,
    val creditor: User,
    val debtor: User,
    val created: String
)
