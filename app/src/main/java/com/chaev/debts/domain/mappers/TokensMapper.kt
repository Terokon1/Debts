package com.chaev.debts.domain.mappers

import com.chaev.debts.data.models.response.AccessResponse
import com.chaev.debts.domain.models.Tokens

object TokensMapper {
    fun fromRaw(r: AccessResponse) = Tokens(r.access ?: throw MappingException("No access token"))
}