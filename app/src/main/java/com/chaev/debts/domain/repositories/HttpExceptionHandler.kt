package com.chaev.debts.domain.repositories

import android.util.Log
import com.chaev.debts.Screens
import com.chaev.debts.domain.cicerone.CiceroneHolder
import com.chaev.debts.utils.Either
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

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
