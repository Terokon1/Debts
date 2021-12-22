package com.chaev.debts.data.api

import com.chaev.debts.data.models.addFriend.AddFriendRequest
import com.chaev.debts.data.models.debt.DebtRequest
import com.chaev.debts.data.models.debt.DebtResponse
import com.chaev.debts.data.models.friendRequest.FriendReqPatch
import com.chaev.debts.data.models.friendRequest.FriendReqResponse
import com.chaev.debts.data.models.friends.FriendResponse
import com.chaev.debts.data.models.login.LoginRequest
import com.chaev.debts.data.models.login.LoginResponse
import com.chaev.debts.data.models.tokens.AccessResponse
import com.chaev.debts.data.models.tokens.RefreshRequest
import retrofit2.http.*

interface ApiService {
    @POST("/accounts/token/")
    suspend fun authorize(@Body login: LoginRequest): LoginResponse

    @POST("/accounts/token/refresh/")
    suspend fun getNewTokens(@Body refreshToken: RefreshRequest): AccessResponse

    @GET("/debts/")
    suspend fun getDebts(
        @Header("Authorization")
        accessToken: String
    ): List<DebtResponse>

    @POST("/debts/")
    suspend fun postDebt(
        @Header("Authorization")
        accessToken: String,
        @Body
        debt: DebtRequest
    ): DebtResponse

    @GET("/accounts/friends/")
    suspend fun getFriends(
        @Header("Authorization")
        accessToken: String
    ): List<FriendResponse>

    @GET("/accounts/friend-requests/")
    suspend fun getFriendRequests(
        @Header("Authorization")
        accessToken: String
    ): List<FriendReqResponse>

    @PATCH("accounts/friend-requests/")
    suspend fun patchFriendRequest(
        @Header("Authorization")
        accessToken: String,
        @Body
        patch: FriendReqPatch
    )

    @POST("accounts/friend-requests/")
    suspend fun postFriendRequest(
        @Header("Authorization")
        accessToken: String,
        @Body
        username: AddFriendRequest
    )
}