package com.chaev.debts.data.models.friends

import com.squareup.moshi.Json

data class FriendResponse (
    @field:Json(name = "id")
    val id: Int?,
    @field:Json (name = "username")
    val username: String?
)