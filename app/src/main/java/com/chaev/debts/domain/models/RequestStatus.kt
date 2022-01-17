package com.chaev.debts.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class RequestStatus {
    @field:Json(name = "accept")
    accept,
    @field:Json(name = "decline")
    decline
}