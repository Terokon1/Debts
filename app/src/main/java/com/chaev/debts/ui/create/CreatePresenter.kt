package com.chaev.debts.ui.create

import com.chaev.debts.App
import com.chaev.debts.data.models.request.DebtRequest
import com.chaev.debts.domain.mappers.MappingException
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePresenter(private val router: Router, private val debtsApiRepository : DebtsApiRepository)  {
    private var view: CreateFragment? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    fun attachView(fragment: CreateFragment) {
        view = fragment
    }

    fun detachView() {
        view = null
    }

    fun navigateBack() {
        router.exit()
    }

    fun postDebt(creditor: String, debtor: String, money: String, description: String) {
        if (creditor.isNotEmpty() && debtor.isNotEmpty() && money.isNotEmpty()) {
            val newDebt = DebtRequest(money, creditor, debtor, description)
            scope.launch {
                debtsApiRepository.postDebt(newDebt)
                navigateBack()
            }
        } else {
            throw MappingException("Failed to post debt")
        }
    }
}