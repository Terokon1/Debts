package com.chaev.debts.domain.models.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "username")
    val username: String
)
