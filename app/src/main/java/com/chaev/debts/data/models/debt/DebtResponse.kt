package com.chaev.debts.data.models.debt

import com.chaev.debts.data.models.base.User
import com.squareup.moshi.Json


data class DebtResponse(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "money")
    val money: String?,
    @field:Json(name = "creditor")
    val creditor: User?,
    @field:Json(name = "debtor")
    val debtor: User?,
    @field:Json(name = "created")
    val created: String?
)
