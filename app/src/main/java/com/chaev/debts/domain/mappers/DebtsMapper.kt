package com.chaev.debts.domain.mappers

import com.chaev.debts.data.models.debt.DebtResponse
import com.chaev.debts.domain.models.Debt

object DebtsMapper {
    fun multipleFromRaw(r: List<DebtResponse>): List<Debt> = r.map{ this.fromRaw(it) }

    fun fromRaw(r: DebtResponse) = Debt(
        r.id ?: throw MappingException("Id"),
        r.money ?: throw MappingException("money"),
        r.creditor ?: throw MappingException("sender"),
        r.debtor ?: throw MappingException("receiver"),
        r.created ?: throw MappingException("created")
    )
}