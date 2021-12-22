package com.chaev.debts.data.exceptionsHandlers

import android.util.Log
import com.chaev.debts.Screens
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import retrofit2.HttpException

class HttpExceptionHandler(
    private val apiRepository: DebtsApiRepository,
    private val router: Router
) {
    suspend fun handle(exception: HttpException): Boolean =
        when (exception.code()) {
            401  -> handleAuthorizationException()
            else -> false
        }


    private suspend fun handleAuthorizationException(): Boolean {
        return when (val r = apiRepository.updateAccessToken(apiRepository.refreshToken)) {
            is Right -> {
                true
            }
            is Left -> {
                Log.d("Debug", "Invalid refresh token", r.value)
                router.replaceScreen(Screens.Login())
                false
            }
        }
    }
}
