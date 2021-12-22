package com.chaev.debts.data.models.friendRequest

import com.chaev.debts.data.models.base.User
import com.squareup.moshi.Json

data class FriendReqResponse(
    @field:Json(name = "id")
    val id: String?,
    @field:Json (name = "user")
    val user: User?
)
