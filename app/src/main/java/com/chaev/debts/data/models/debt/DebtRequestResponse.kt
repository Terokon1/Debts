package com.chaev.debts.data.models.debt

import com.chaev.debts.domain.models.base.User
import com.squareup.moshi.Json

data class DebtRequestResponse(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "money")
    val money: String?,
    @field:Json(name = "creditor")
    val creditor: User?,
    @field:Json(name = "debtor")
    val debtor: User?,
    @field:Json(name = "is_yours")
    val isYours: Boolean?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "created")
    val created: String?,
    @field:Json(name = "is_active")
    val isActive: Boolean?
)
