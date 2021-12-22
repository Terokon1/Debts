package com.chaev.debts.domain.mappers

import com.chaev.debts.data.models.login.LoginResponse
import com.chaev.debts.domain.exceptions.MappingException
import com.chaev.debts.domain.models.Login

object LoginMapper {
    fun fromRaw(r: LoginResponse) = Login(
        r.accessToken ?: throw MappingException("access token"),
        r.refreshToken ?: throw MappingException("refresh token")
    )
}