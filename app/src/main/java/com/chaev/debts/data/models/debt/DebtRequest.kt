package com.chaev.debts.data.models.debt

import com.squareup.moshi.Json


data class DebtRequest(
    @field:Json(name = "money")
    val money: String?,
    @field:Json (name = "creditor")
    val creditor: String?,
    @field:Json (name = "debtor")
    val debtor: String?,
    @field:Json (name = "description")
    val description: String?
)

