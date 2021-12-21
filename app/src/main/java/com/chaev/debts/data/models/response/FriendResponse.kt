package com.chaev.debts.data.models.response

import com.squareup.moshi.Json

data class FriendResponse (
    @field:Json(name = "id")
    val id: Int?,
    @field:Json (name = "username")
    val username: String?
)