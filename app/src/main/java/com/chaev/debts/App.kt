package com.chaev.debts

import android.app.Application
import com.chaev.debts.data.api.RetrofitBuilder
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.presentation.create.CreatePresenter
import com.chaev.debts.presentation.debts.DebtsPresenter
import com.chaev.debts.presentation.login.LoginPresenter
import com.github.terrakok.cicerone.Cicerone

class App : Application() {
    private val cicerone = Cicerone.create()
    private val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val debtsPresenter = DebtsPresenter(router)
    val createPresenter = CreatePresenter(router)
    val loginPresenter = LoginPresenter(router)
    val debtsApiRepository = DebtsApiRepository(RetrofitBuilder.apiService)
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }
}