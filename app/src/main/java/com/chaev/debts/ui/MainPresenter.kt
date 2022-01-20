package com.chaev.debts.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.chaev.debts.Screens
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.AppConsts
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPresenter(
    private val debtsApiRepository: DebtsApiRepository,
    private val router: Router,
    private val prefs: SharedPreferences
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: MainActivity? = null

    fun attachView(activity: MainActivity) {
        view = activity
    }

    fun detachView() {
        view = null
    }

    fun checkAuthorization() {
        scope.launch {
            prefs.getString(AppConsts.REFRESH_TOKEN_KEY, "")
                ?.let { token ->
                    when (debtsApiRepository.verifyToken(token)
                    ) {
                        is Right -> {
                            debtsApiRepository.refreshToken = token
                            view?.performLogin()
                            router.replaceScreen(Screens.DebtsPager())
                        }
                        is Left -> {
                            prefs.edit().apply {
                                putString(AppConsts.REFRESH_TOKEN_KEY, "")
                                putString(AppConsts.USERNAME_KEY, "")
                                apply()
                            }
                            router.replaceScreen(Screens.Login())
                        }
                    }
                }
        }
    }

    fun saveTokens() {
        prefs.edit().apply {
            putString(AppConsts.REFRESH_TOKEN_KEY, debtsApiRepository.refreshToken)
            apply()
        }
    }

    fun clearPrefs() {
        prefs.edit().apply {
            putString(AppConsts.REFRESH_TOKEN_KEY, "")
            putString(AppConsts.USERNAME_KEY, "")
            apply()
        }
    }
}