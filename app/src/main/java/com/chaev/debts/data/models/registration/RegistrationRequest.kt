package com.chaev.debts.data.models.registration

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationRequest(
    @field:Json(name = "username")
    val username: String,
    @field:Json(name = "password")
    val password: String
)