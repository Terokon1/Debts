package com.chaev.debts.data.models.response

import com.squareup.moshi.Json

data class LoginResponse(
    @field:Json(name = "access")
    val accessToken: String?,
    @field:Json (name = "refresh")
    val refreshToken: String?
)