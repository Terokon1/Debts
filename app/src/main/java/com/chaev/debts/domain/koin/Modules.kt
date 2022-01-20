package com.chaev.debts.domain.koin

import android.content.Context
import android.content.SharedPreferences
import com.chaev.debts.data.api.RetrofitBuilder
import com.chaev.debts.domain.cicerone.CiceroneHolder
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.data.exceptionsHandlers.HttpExceptionHandler
import com.chaev.debts.ui.friend.add.AddFriendPresenter
import com.chaev.debts.ui.debt.create.CreatePresenter
import com.chaev.debts.ui.debt.create.bottomSheet.BottomSheetPresenter
import com.chaev.debts.ui.debt.debtRequest.DebtRequestPresenter
import com.chaev.debts.ui.debt.debts.DebtsPresenter
import com.chaev.debts.ui.friend.friendRequest.FriendRequestPresenter
import com.chaev.debts.ui.friend.friends.FriendsPresenter
import com.chaev.debts.ui.meeting.login.LoginPresenter
import com.chaev.debts.ui.meeting.registration.RegistrationPresenter
import com.chaev.debts.utils.AppConsts
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    factory<FriendsPresenter> {
        FriendsPresenter(
            get<CiceroneHolder>().router,
            get<DebtsApiRepository>(),
            get<HttpExceptionHandler>()
        )
    }
    factory<DebtsPresenter> {
        DebtsPresenter(
            get<CiceroneHolder>().router,
            get<DebtsApiRepository>(),
            get<HttpExceptionHandler>()
        )
    }
    factory {
        LoginPresenter(
            get<CiceroneHolder>().router,
            get<DebtsApiRepository>(),
            get<SharedPreferences>()
        )
    }
    factory {
        CreatePresenter(
            get<CiceroneHolder>().router,
            get<HttpExceptionHandler>(),
            get<DebtsApiRepository>()
        )
    }
    factory { FriendRequestPresenter(get<DebtsApiRepository>(), get<HttpExceptionHandler>()) }
    factory {
        AddFriendPresenter(
            get<CiceroneHolder>().router,
            get<DebtsApiRepository>(),
            get<HttpExceptionHandler>()
        )
    }
    factory {
        DebtRequestPresenter(
            get<DebtsApiRepository>(),
            get<HttpExceptionHandler>(),
            get<CiceroneHolder>().router
        )
    }
    factory { BottomSheetPresenter(get<DebtsApiRepository>(), get<HttpExceptionHandler>()) }
    factory { RegistrationPresenter(get<CiceroneHolder>().router, get<DebtsApiRepository>()) }
    single {
        androidApplication().applicationContext.getSharedPreferences(
            AppConsts.TOKENS_KEY,
            Context.MODE_PRIVATE
        )
    }
    single { DebtsApiRepository(RetrofitBuilder.apiService, get<SharedPreferences>()) }
    single { CiceroneHolder() }
    single { HttpExceptionHandler(get<DebtsApiRepository>(), get<CiceroneHolder>().router) }

}