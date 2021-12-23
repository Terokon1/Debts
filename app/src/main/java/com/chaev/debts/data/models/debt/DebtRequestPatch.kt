package com.chaev.debts.data.models.debt

import com.chaev.debts.domain.models.RequestStatus
import com.squareup.moshi.Json

data class DebtRequestPatch(
    @field:Json(name = "debt_request_id")
    val debtRequestId: String,
    @field:Json(name = "status")
    val status: RequestStatus
)