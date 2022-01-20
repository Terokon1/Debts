package com.chaev.debts.ui.meeting.login

import android.content.SharedPreferences
import com.chaev.debts.Screens
import com.chaev.debts.data.models.login.LoginRequest
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.*
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginPresenter(
    private val router: Router,
    private val debtsApiRepository: DebtsApiRepository,
    private val prefs: SharedPreferences
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
        router.replaceScreen(Screens.DebtsPager())
    }
    fun navigateRegistration(){
        router.navigateTo(Screens.Registration())
    }

    fun onLoginClicked(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            scope.launch {
                when (val r = debtsApiRepository.authorize(LoginRequest(username, password))) {
                    is Left -> view?.showAuthorizeError()
                    is Right -> {
                        debtsApiRepository.setupTokens(r.value.accessToken, r.value.refreshToken)
                        prefs.edit().apply {
                            putString(AppConsts.REFRESH_TOKEN_KEY, r.value.refreshToken)
                            putString(AppConsts.USERNAME_KEY, username)
                            apply()
                        }
                        view?.setUsername()
                        navigateSuccessLogin()
                    }
                }
            }
        }
    }

}
