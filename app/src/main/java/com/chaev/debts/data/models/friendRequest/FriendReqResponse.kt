package com.chaev.debts.data.models.friendRequest

import com.chaev.debts.domain.models.base.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendReqResponse(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "from_user")
    val fromUser: User?,
    @field:Json(name = "to_user")
    val toUser: User?
)
