package com.chaev.debts.domain.koin

import com.chaev.debts.data.api.RetrofitBuilder
import com.chaev.debts.domain.cicerone.CiceroneHolder
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.data.exceptionsHandlers.HttpExceptionHandler
import com.chaev.debts.ui.friend.add.AddFriendPresenter
import com.chaev.debts.ui.debt.create.CreatePresenter
import com.chaev.debts.ui.debt.debtRequest.DebtRequestPresenter
import com.chaev.debts.ui.debt.debts.DebtsPresenter
import com.chaev.debts.ui.friend.friendRequest.FriendRequestPresenter
import com.chaev.debts.ui.friend.friends.FriendsPresenter
import com.chaev.debts.ui.meeting.login.LoginPresenter
import org.koin.dsl.module

val appModule = module {
    factory<FriendsPresenter> {
        FriendsPresenter(
            get<CiceroneHolder>().router,
            get<DebtsApiRepository>()
        )
    }
    factory<DebtsPresenter> {
        DebtsPresenter(
            get<CiceroneHolder>().router,
            get<DebtsApiRepository>(),
            get<HttpExceptionHandler>()
        )
    }
    factory { LoginPresenter(get<CiceroneHolder>().router, get<DebtsApiRepository>()) }
    factory { CreatePresenter(get<CiceroneHolder>().router, get<DebtsApiRepository>()) }
    factory { FriendRequestPresenter(get<DebtsApiRepository>()) }
    factory { AddFriendPresenter(get<CiceroneHolder>().router, get<DebtsApiRepository>()) }
    factory {
        DebtRequestPresenter(
            get<DebtsApiRepository>(),
            get<HttpExceptionHandler>(),
            get<CiceroneHolder>().router
        )
    }
    single { DebtsApiRepository(RetrofitBuilder.apiService) }
    single { CiceroneHolder() }
    single { HttpExceptionHandler(get<DebtsApiRepository>(), get<CiceroneHolder>().router) }
}