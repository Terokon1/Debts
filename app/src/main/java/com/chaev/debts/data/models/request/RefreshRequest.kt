package com.chaev.debts.data.models.request

import com.squareup.moshi.Json

data class RefreshRequest(
    @Json(name = "refresh")
    val refresh: String
)
