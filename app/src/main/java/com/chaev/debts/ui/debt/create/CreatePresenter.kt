package com.chaev.debts.ui.debt.create

import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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


}