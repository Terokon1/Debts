package com.chaev.debts.domain.mappers

import com.chaev.debts.data.models.response.AccessResponse
import com.chaev.debts.domain.models.Access

object AccessMapper {
    fun fromRaw(r: AccessResponse) = Access(r.access ?: throw MappingException("No access token"))
}