package com.chaev.debts.domain.mappers

import com.chaev.debts.data.models.debt.DebtRequestResponse
import com.chaev.debts.domain.exceptions.MappingException
import com.chaev.debts.domain.models.debt.DebtRequest

object DebtRequestMapper {
    fun fromRaw(r: DebtRequestResponse) = DebtRequest(
        r.id ?: throw MappingException("No id"),
        r.money ?: throw MappingException("No money"),
        r.creditor ?: throw MappingException("No creditor"),
        r.debtor ?: throw MappingException("No debtor"),
        r.isYours ?: throw MappingException("No isYours"),
        r.description ?: throw MappingException("No description"),
        r.created ?: throw MappingException("No created"),
        r.isActive ?: throw MappingException("No isActive"),
    )
}