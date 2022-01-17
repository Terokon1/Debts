package com.chaev.debts.domain.mappers

import com.chaev.debts.data.models.friendRequest.FriendReqResponse
import com.chaev.debts.domain.exceptions.MappingException
import com.chaev.debts.domain.models.FriendRequest

object FriendRequestsMapper {
    fun multipleFromRaw(r: List<FriendReqResponse>): List<FriendRequest> {
        return r.map { this.fromRaw(it) }
    }

    private fun fromRaw(r: FriendReqResponse) = FriendRequest(
        r.id ?: throw MappingException("id"),
        r.fromUser ?: throw MappingException("user"),
        r.toUser ?: throw MappingException("user")
    )
}