package com.chaev.debts.data.api

import com.chaev.debts.data.models.request.DebtRequest
import com.chaev.debts.data.models.request.LoginRequest
import com.chaev.debts.data.models.response.DebtResponse
import com.chaev.debts.data.models.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/accounts/token/")
    suspend fun authorize(@Body login: LoginRequest): LoginResponse

    @GET("/debts/")
    suspend fun getDebts(
        @Header("Authorization")
        accessToken: String
    ): List<DebtResponse>

    @POST("/debts/")
    suspend fun postDebt(@Body debt: DebtRequest): DebtResponse

}