package com.chaev.debts.data.models.tokens

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshRequest(
    @Json(name = "refresh")
    val refresh: String
)
