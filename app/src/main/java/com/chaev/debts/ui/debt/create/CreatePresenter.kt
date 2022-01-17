package com.chaev.debts.ui.debt.create

import android.util.Log
import com.chaev.debts.data.exceptionsHandlers.HttpExceptionHandler
import com.chaev.debts.data.models.debt.DebtRequestRequest
import com.chaev.debts.domain.models.Friend
import com.chaev.debts.domain.models.base.User
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePresenter(
    private val router: Router,
    private val handler: HttpExceptionHandler,
    private val debtsApiRepository: DebtsApiRepository
) {
    private var view: CreateFragment? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    var result: Friend = Friend("", "")
    var me: User = User("", "")

    init {
        scope.launch {
            me = getMyInfo()
        }
    }

    fun attachView(fragment: CreateFragment) {
        view = fragment
    }

    fun detachView() {
        view = null
    }

    fun navigateBack() {
        router.exit()
    }


    fun sendDebtRequest(
        money: String,
        description: String,
        creditorMode: Boolean
    ) {
        scope.launch {
            if (creditorMode) {
                val request = DebtRequestRequest(money, me.id, result.id, description)
                when (val r =
                    handler.runWithAuthRetry({ debtsApiRepository.postDebtRequest(request) })) {
                    is Right -> {
                        view?.showSuccessMessage()
                        navigateBack()
                    }
                    is Left -> {
                        view?.showErrorMessage()
                        Log.d("debtRequest", r.value.toString())
                    }
                }
            }
        }
    }

    private suspend fun getMyInfo(): User =
        when (val r = handler.runWithAuthRetry({ debtsApiRepository.getMyInfo() })) {
            is Right -> r.value
            is Left -> User("", "")
        }

}