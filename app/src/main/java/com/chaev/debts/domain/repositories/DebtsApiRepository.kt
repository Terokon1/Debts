package com.chaev.debts.domain.repositories

import com.chaev.debts.data.api.ApiService
import com.chaev.debts.data.models.request.DebtRequest
import com.chaev.debts.data.models.request.LoginRequest
import com.chaev.debts.data.models.response.LoginResponse
import com.chaev.debts.domain.mappers.DebtsMapper
import com.chaev.debts.domain.mappers.LoginMapper
import com.chaev.debts.domain.models.Debt
import com.chaev.debts.domain.models.Login
import com.chaev.debts.utils.Either

class DebtsApiRepository(private val api: ApiService) {
    var accessToken: String = ""

    fun setupAccessToken(token: String) {
        accessToken = "Bearer $token"
    }

    suspend fun getDebts(): Either<Exception, List<Debt>> = Either.of {
        DebtsMapper.multipleFromRaw(api.getDebts(accessToken))
    }

    suspend fun postDebt(debt: DebtRequest) {
        api.postDebt(debt)
    }

    suspend fun authorize(login: LoginRequest): Either<Exception, Login> = Either.of {
       LoginMapper.fromRaw(api.authorize(login))
    }
}