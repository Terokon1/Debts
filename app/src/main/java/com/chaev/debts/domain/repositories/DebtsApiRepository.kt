package com.chaev.debts.domain.repositories

import android.content.SharedPreferences
import android.util.Log
import com.chaev.debts.data.api.ApiService
import com.chaev.debts.data.models.addFriend.AddFriendRequest
import com.chaev.debts.data.models.debt.DebtRequestPatch
import com.chaev.debts.data.models.debt.DebtRequestRequest
import com.chaev.debts.data.models.friendRequest.FriendReqPatch
import com.chaev.debts.data.models.login.LoginRequest
import com.chaev.debts.data.models.tokens.RefreshRequest
import com.chaev.debts.data.models.tokens.TokenRequest
import com.chaev.debts.domain.mappers.*
import com.chaev.debts.domain.models.debt.Debt
import com.chaev.debts.domain.models.Friend
import com.chaev.debts.domain.models.FriendRequest
import com.chaev.debts.domain.models.Login
import com.chaev.debts.domain.models.base.User
import com.chaev.debts.domain.models.debt.DebtRequest
import com.chaev.debts.utils.AppConsts
import com.chaev.debts.utils.Either

class DebtsApiRepository(private val api: ApiService, private val prefs: SharedPreferences) {
    var accessToken: String = ""
    var refreshToken: String = ""

    fun setupTokens(access: String, refresh: String) {
        accessToken = "Bearer $access"
        refreshToken = refresh
        prefs.edit().apply {
            putString(AppConsts.REFRESH_TOKEN_KEY, refresh)
            apply()
        }
    }

    suspend fun updateAccessToken(token: String) = Either.of {
        val tokens = TokensMapper.fromRaw(api.getNewTokens(RefreshRequest(token)))
        setupTokens(tokens.access, tokens.refresh)
    }

    suspend fun getDebts(): Either<Exception, List<Debt>> = Either.of {
        DebtsMapper.multipleFromRaw(api.getDebts(accessToken))
    }

    suspend fun getDebtRequests(): Either<Exception, List<DebtRequest>> = Either.of {
        api.getDebtRequests(accessToken).map { DebtRequestMapper.fromRaw(it) }
    }

    suspend fun patchDebtRequest(patch: DebtRequestPatch): Either<Exception, Unit> = Either.of {
        api.patchDebtRequest(accessToken, patch)
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

    suspend fun patchFriendRequest(patch: FriendReqPatch): Either<Exception, Unit> = Either.of {
        api.patchFriendRequest(accessToken, patch)
    }

    suspend fun postFriendRequest(username: String): Either<Exception, Unit> = Either.of {
        api.postFriendRequest(accessToken, AddFriendRequest(username))
    }

    suspend fun verifyToken(refreshToken: String): Either<Exception, Unit> = Either.of {
        api.verifyToken(TokenRequest(refreshToken))
    }

    suspend fun postDebtRequest(request: DebtRequestRequest): Either<Exception, Unit> = Either.of {
        api.postDebtRequest(accessToken, request)
    }

    suspend fun getMyInfo(): Either<Exception, User> = Either.of {
        api.getMyInfo(accessToken)
    }
}