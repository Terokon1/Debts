package com.chaev.debts.data.models.tokens

import com.squareup.moshi.Json

data class RefreshRequest(
    @Json(name = "refresh")
    val refresh: String
)
