package com.chaev.debts.data.models.tokens

import com.squareup.moshi.Json

data class TokenRequest(
    @field:Json(name = "token")
    val token: String
)
