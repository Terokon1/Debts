package com.chaev.debts.presentation.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.chaev.debts.App
import com.chaev.debts.Screens
import com.chaev.debts.data.models.request.LoginRequest
import com.chaev.debts.domain.models.Login
import com.chaev.debts.presentation.debts.DebtsFragment
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter(private val router: Router) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: LoginFragment? = null
    fun attachView(fragment: LoginFragment) {
        view = fragment
    }

    fun detachView() {
        view = null
    }

    fun successLogin() {
        router.replaceScreen(Screens.Debts())
    }

    fun onLoginClicked(username: String, password: String, context: Context) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            scope.launch {
                val login = authorize(username, password)
                if (login == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Authorization failed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    App.INSTANCE.debtsApiRepository.setupAccessToken(login.accessToken)
                    successLogin()
                }
            }
        }
    }

    private suspend fun authorize(username: String, password: String): Login? =
        when (val r = App.INSTANCE.debtsApiRepository.authorize(LoginRequest(username, password))) {
            is Right -> {
                Log.d("Debug", "${r.value}")
                r.value
            }
            is Left -> {
                Log.d("Debug", "No authorize", r.value)
                null
            }
        }
}