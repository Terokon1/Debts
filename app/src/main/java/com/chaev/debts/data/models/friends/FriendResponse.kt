package com.chaev.debts.data.models.friends

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendResponse(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "username")
    val username: String?
)