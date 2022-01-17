package com.chaev.debts.data.models.debt

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DebtRequestRequest(
    @field:Json(name = "money")
    val money: String,
    @field:Json(name = "creditor_id")
    val creditorId: String,
    @field:Json(name = "debtor_id")
    val debtorId: String,
    @field:Json(name = "description")
    val description: String
)
