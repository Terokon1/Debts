package com.chaev.debts.data.models.addFriend

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class AddFriendRequest(
    @field:Json(name = "username")
    val username: String
)