package com.chaev.debts.domain.models

import com.chaev.debts.data.models.base.User

data class Debt(
    val id: String,
    val money: String,
    val creditor: User,
    val debtor: User,
    val created: String
)
