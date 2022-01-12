package com.chaev.debts.data.exceptionsHandlers

import android.util.Log
import com.chaev.debts.Screens
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Either
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
            401 -> handleAuthorizationException()
            else -> false
        }


    private suspend fun handleAuthorizationException(): Boolean {
        return when (val r = apiRepository.updateAccessToken(apiRepository.refreshToken)) {
            is Right -> {
                true
            }
            is Left -> {
                Log.d("Debug", "Invalid refresh token", r.value)
                false
            }
        }
    }

    suspend fun <T> runWithAuthRetry(
        action: suspend () -> Either<Exception, T>,
        retries: Int = 4
    ): Either<Exception, T> {
        var attempts = retries
        var isSuccessful = false
        return when (val r = action.invoke()) {
            is Right -> {
                r
            }
            is Left ->
                when (val e = r.value) {
                    is HttpException -> {
                        while (!isSuccessful && attempts > 0) {
                            isSuccessful = handle(e)
                            attempts -= 1
                        }
                        if (isSuccessful) {
                            action.invoke()
                        } else {
                            router.replaceScreen(Screens.Login())
                            r
                        }
                    }
                    else -> r
                }
        }
    }

    suspend fun <T, A> runWithAuthRetryArgs(
        action: suspend (A) -> Either<Exception, T>,
        args: A,
        retries: Int = 4
    ): Either<Exception, T> {
        var attempts = retries
        var isSuccessful = false
        return when (val r = action.invoke(args)) {
            is Right -> {
                r
            }
            is Left ->
                when (val e = r.value) {
                    is HttpException -> {
                        while (!isSuccessful && attempts > 0) {
                            isSuccessful = handle(e)
                            attempts -= 1
                        }
                        if (isSuccessful) {
                            action.invoke(args)
                        } else {
                            router.replaceScreen(Screens.Login())
                            r
                        }
                    }
                    else -> r
                }
        }
    }
}
