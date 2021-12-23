package com.chaev.debts.domain.models

import com.chaev.debts.domain.models.base.User

data class FriendRequest (
    val id: String,
    val user: User
        )
