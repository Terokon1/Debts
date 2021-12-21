package com.chaev.debts.data.models.base

import com.squareup.moshi.Json


data class User(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "username")
    val username: String
)
