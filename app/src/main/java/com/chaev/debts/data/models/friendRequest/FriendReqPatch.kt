package com.chaev.debts.data.models.friendRequest


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendReqPatch(
    @field:Json(name = "friend_request_id")
    val id: String,
    @field:Json(name = "status")
    val status: String
)