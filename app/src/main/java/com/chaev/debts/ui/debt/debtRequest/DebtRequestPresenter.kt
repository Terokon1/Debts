package com.chaev.debts.ui.debt.debtRequest

import android.util.Log
import com.chaev.debts.Screens
import com.chaev.debts.data.exceptionsHandlers.HttpExceptionHandler
import com.chaev.debts.data.models.debt.DebtRequestPatch
import com.chaev.debts.data.models.friendRequest.FriendReqPatch
import com.chaev.debts.domain.models.RequestStatus
import com.chaev.debts.domain.models.debt.DebtRequest
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DebtRequestPresenter(
    private val debtsApiRepository: DebtsApiRepository,
    private val handler: HttpExceptionHandler,
    private val router: Router
) {
    private var view: DebtRequestFragment? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private var items = emptyList<DebtRequest>()

    init {
        scope.launch {
            items = getDebtRequests()
            withContext(Dispatchers.Main) { view?.updateRecycler(items) }
        }
    }

    fun attachView(fragment: DebtRequestFragment) {
        view = fragment
        view?.updateRecycler(items)

    }

    fun detachView() {
        view = null
    }

    fun navigateCreate() {
        router.navigateTo(Screens.Create())
    }

    private suspend fun getDebtRequests(): List<DebtRequest> =
        when (val r = handler.runWithAuthRetry(debtsApiRepository::getDebtRequests)) {
            is Right -> r.value
            is Left -> when (val e = r.value) {
                is HttpException -> if (handler.handle(e)) {
                    getDebtRequests()
                } else {
                    emptyList()
                }
                else -> emptyList()
            }
        }

    fun onResponseClicked(id: String, status: RequestStatus) {
        scope.launch {
            when (val r = handler.runWithAuthRetryArgs(
                debtsApiRepository::patchDebtRequest,
                DebtRequestPatch(id, status)
            )) {
                is Right -> {
                    items = items.filter { it.id != id }
                    withContext(Dispatchers.Main) { view?.updateRecycler(items) }
                }
                is Left -> {
                    when (val e = r.value) {
                        is HttpException -> if (handler.handle(e)) {
                            onResponseClicked(id, status)
                        } else {
                            Log.d("Debug", "Failed to patch", r.value)
                            view?.showPatchError()
                        }
                        else -> view?.showPatchError()
                    }
                }
            }
        }
    }
}
