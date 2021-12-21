package com.chaev.debts

import android.app.Application
import com.chaev.debts.data.api.RetrofitBuilder
import com.chaev.debts.domain.koin.appModule
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.ui.create.CreatePresenter
import com.chaev.debts.ui.debts.DebtsPresenter
import com.chaev.debts.ui.login.LoginPresenter
import com.github.terrakok.cicerone.Cicerone
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }
}