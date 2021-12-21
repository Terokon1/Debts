package com.chaev.debts.ui.debts

import android.util.Log
import com.chaev.debts.App
import com.chaev.debts.Screens
import com.chaev.debts.domain.models.Debt
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.domain.repositories.HttpExceptionHandler
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception

class DebtsPresenter(
    private val router: Router,
    private val debtsApiRepository: DebtsApiRepository,
    private val handler: HttpExceptionHandler
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: DebtsFragment? = null

    init {
        scope.launch {
            val debts = getDebts()
            withContext(Dispatchers.Main) { view?.fillRecycler(debts) }
        }
    }

    fun attachView(fragment: DebtsFragment) {
        view = fragment
    }

    fun detachView() {
        view = null
    }

    private suspend fun getDebts(): List<Debt> =
        when (val r = debtsApiRepository.getDebts()) {
            is Right -> {
                Log.d("Debug", "${r.value}")
                r.value
            }
            is Left -> when (val e = r.value) {
                is HttpException -> if (handler.handle(e)) {
                    getDebts()
                } else {
                    emptyList()
                }
                else -> emptyList()
            }
        }


    fun onCreateClicked() {
        router.navigateTo(Screens.Create())
    }

}