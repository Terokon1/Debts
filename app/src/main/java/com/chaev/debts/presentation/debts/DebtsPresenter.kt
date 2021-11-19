package com.chaev.debts.presentation.debts

import android.util.Log
import com.chaev.debts.App
import com.chaev.debts.Screens
import com.chaev.debts.domain.models.Debt
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DebtsPresenter(private val router: Router) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: DebtsFragment? = null
    fun attachView(fragment: DebtsFragment) {
        view = fragment
    }

    fun detachView() {
        view = null
    }

    fun collectDebts() {
        scope.launch {
            val debts = getDebts()
            withContext(Dispatchers.Main) { view?.fillRecycler(debts) }
        }
    }

    private suspend fun getDebts(): List<Debt> =
        when (val r = App.INSTANCE.debtsApiRepository.getDebts()) {
            is Right -> {
                Log.d("Debug", "${r.value}")
                r.value
            }
            is Left -> {
                Log.d("Debug", "No debt", r.value)
                listOf()
            }
        }

    fun onCreateClicked() {
        router.navigateTo(Screens.Create())
    }

}
