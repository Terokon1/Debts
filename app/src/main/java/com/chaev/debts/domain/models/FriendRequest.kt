package com.chaev.debts.domain.models

import com.chaev.debts.domain.models.base.User
import com.squareup.moshi.Json

data class FriendRequest(
    val id: String,
    val fromUser: User,
    val toUser: User
)
