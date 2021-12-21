package com.chaev.debts.domain.models

import com.squareup.moshi.Json

enum class RequestStatus {
    @field:Json(name = "accept")
    Accept,
    @field:Json(name = "decline")
    Decline
}