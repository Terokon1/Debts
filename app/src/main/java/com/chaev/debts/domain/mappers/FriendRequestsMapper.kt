package com.chaev.debts.domain.mappers

import com.chaev.debts.data.models.response.FriendReqResponse
import com.chaev.debts.domain.models.FriendRequest

object FriendRequestsMapper {
    fun multipleFromRaw(r: List<FriendReqResponse>): List<FriendRequest> {
        return r.map { this.fromRaw(it) }
    }

    private fun fromRaw(r: FriendReqResponse) = FriendRequest(
        r.id ?: throw MappingException("id"),
        r.user ?: throw MappingException("user")
    )
}