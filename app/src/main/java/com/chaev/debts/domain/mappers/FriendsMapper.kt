package com.chaev.debts.domain.mappers

import com.chaev.debts.data.models.friends.FriendResponse
import com.chaev.debts.domain.exceptions.MappingException
import com.chaev.debts.domain.models.Friend

object FriendsMapper {
    fun multipleFromRaw(r: List<FriendResponse>): List<Friend> {
        return r.map { this.fromRaw(it) }
    }

    private fun fromRaw(r: FriendResponse) = Friend(
        r.id ?: throw MappingException("id"),
        r.username ?: throw MappingException("username")
    )
}