package com.chaev.debts.domain.repositories

import com.chaev.debts.data.api.ApiService
import com.chaev.debts.data.models.request.*
import com.chaev.debts.domain.mappers.*
import com.chaev.debts.domain.models.Debt
import com.chaev.debts.domain.models.Friend
import com.chaev.debts.domain.models.FriendRequest
import com.chaev.debts.domain.models.Login
import com.chaev.debts.utils.Either

class DebtsApiRepository(private val api: ApiService) {
    var accessToken: String = ""
    var refreshToken: String = ""

    fun setupTokens(access: String, refresh: String) {
        accessToken = "Bearer $access"
        refreshToken = refresh
    }

    suspend fun updateAccessToken(token: String) = Either.of {
        accessToken = "Bearer ${AccessMapper.fromRaw(api.getNewAccessToken(RefreshRequest(token))).token}"
    }

    suspend fun getDebts(): Either<Exception, List<Debt>> = Either.of {
        DebtsMapper.multipleFromRaw(api.getDebts(accessToken))
    }

    suspend fun postDebt(debt: DebtRequest) {
        api.postDebt(accessToken, debt)
    }

    suspend fun authorize(login: LoginRequest): Either<Exception, Login> = Either.of {
        LoginMapper.fromRaw(api.authorize(login))
    }

    suspend fun getFriends(): Either<Exception, List<Friend>> = Either.of {
        FriendsMapper.multipleFromRaw(api.getFriends(accessToken))
    }

    suspend fun getFriendRequests(): Either<Exception, List<FriendRequest>> = Either.of {
        FriendRequestsMapper.multipleFromRaw(api.getFriendRequests(accessToken))
    }

    suspend fun patchFriendRequest(patch: FriendReqPatch) {
        api.patchFriendRequest(accessToken, patch)
    }

    suspend fun postFriendRequest(username: String) {
        api.postFriendRequest(accessToken, AddFriendRequest(username))
    }
}