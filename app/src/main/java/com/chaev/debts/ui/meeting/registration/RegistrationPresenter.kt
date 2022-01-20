package com.chaev.debts.ui.meeting.registration

import android.util.Log
import com.chaev.debts.data.models.registration.RegistrationRequest
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.ui.meeting.login.LoginFragment
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationPresenter(
    private val router: Router,
    private val debtsApiRepository: DebtsApiRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: RegistrationFragment? = null
    fun attachView(fragment: RegistrationFragment) {
        view = fragment
    }

    fun detachView() {
        view = null
    }

    private fun navigateBack(){
        router.exit()
    }

    fun register(username: String, password: String) {
        scope.launch {
            when (val r = debtsApiRepository.register(RegistrationRequest(username, password))) {
                is Right -> {
                    navigateBack()
                    view?.showSuccess()
                }
                is Left -> {
                    view?.showError()
                    Log.d("zxc", r.value.toString())
                }
            }
        }
    }
}