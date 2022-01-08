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
        router.replaceScreen(Screens.DebtsPager())
    }


    fun onLoginClicked(username: String, password: String, prefs: SharedPreferences) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            scope.launch {
                when (val r = debtsApiRepository.authorize(LoginRequest(username, password))) {
                    is Left -> view?.showAuthorizeError()
                    is Right -> {
                        debtsApiRepository.setupTokens(r.value.accessToken, r.value.refreshToken)
                        prefs.edit().apply {
                            putString(REFRESH_TOKEN_KEY, r.value.refreshToken)
                            apply()
                        }
                        USERNAME = username
                        navigateSuccessLogin()
                    }
                }
            }
        }
    }

    fun checkAuthorization(prefs: SharedPreferences) {
        scope.launch {
            prefs.getString(REFRESH_TOKEN_KEY, "")
                ?.let {
                    when (val r =
                        debtsApiRepository.verifyToken(it)
                    ) {
                        is Right -> {
                            debtsApiRepository.refreshToken = it
                            navigateSuccessLogin()
                        }
                        is Left -> {
                            prefs.edit().apply {
                                putString(REFRESH_TOKEN_KEY, "")
                                apply()
                            }
                        }
                    }
                }
        }
    }
}
