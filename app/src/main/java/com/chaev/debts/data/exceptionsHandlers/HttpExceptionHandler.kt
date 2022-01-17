package com.chaev.debts.data.exceptionsHandlers

import android.util.Log
import com.chaev.debts.Screens
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Either
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.net.HttpURLConnection

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
        retries: Int = 4,
        delay: Int = 250
    ): Either<Exception, T> {
        var attempts = retries
        var isSuccessful = false
        return when (val r = action.invoke()) {
            is Left ->
                when (val e = r.value) {
                    is HttpException -> {
                        while (!isSuccessful && attempts > 0) {
                            delay(delay.toLong())
                            isSuccessful = handle(e)
                            attempts -= 1
                        }
                        when {
                            isSuccessful -> {
                                action.invoke()
                            }
                            e.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> {
                                router.replaceScreen(Screens.Login())
                                r
                            }
                            else -> {
                                r
                            }
                        }
                    }
                    else -> r
                }
            else -> r
        }
    }
}
