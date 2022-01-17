package com.chaev.debts.data.models.tokens

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenRequest(
    @field:Json(name = "token")
    val token: String
)
