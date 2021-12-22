package com.chaev.debts.domain.mappers

import com.chaev.debts.data.models.tokens.AccessResponse
import com.chaev.debts.domain.exceptions.MappingException
import com.chaev.debts.domain.models.Tokens

object TokensMapper {
    fun fromRaw(r: AccessResponse) = Tokens(
        r.access ?: throw MappingException("No access token"),
        r.refresh ?: throw MappingException("No refresh token")
    )
}