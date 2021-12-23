package com.chaev.debts.ui.debt.create

import com.chaev.debts.data.models.debt.DebtRequest
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePresenter(
    private val router: Router,
    private val debtsApiRepository: DebtsApiRepository
) {
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
                when (val r = debtsApiRepository.postDebt(newDebt)) {
                    is Right -> {
                        navigateBack()
                    }
                    is Left -> {
                    }
                }

            }
        } else {
            throw Exception("Failed to create")
        }
    }
}