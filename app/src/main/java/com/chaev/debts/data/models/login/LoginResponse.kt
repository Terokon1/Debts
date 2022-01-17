package com.chaev.debts.data.models.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @field:Json(name = "access")
    val accessToken: String?,
    @field:Json(name = "refresh")
    val refreshToken: String?
)