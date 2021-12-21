package com.chaev.debts.ui.login

import com.chaev.debts.Screens
import com.chaev.debts.data.models.request.LoginRequest
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.chaev.debts.utils.USERNAME
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter(
    private val router: Router,
    private val debtsApiRepository: DebtsApiRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: LoginFragment? = null
    fun attachView(fragment: LoginFragment) {
        view = fragment
    }

    fun detachView() {
        view = null
    }

    private fun navigateSuccessLogin() {
        router.replaceScreen(Screens.Debts())
    }

    fun onLoginClicked(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            scope.launch {
                when (val r = debtsApiRepository.authorize(LoginRequest(username, password))) {
                    is Left -> view?.showAuthorizeError()
                    is Right -> {
                        debtsApiRepository.setupTokens(r.value.accessToken, r.value.refreshToken)
                        USERNAME = username
                        withContext(Dispatchers.Main) { view?.showNavigation() }
                        navigateSuccessLogin()
                    }
                }
            }
        }
    }
}