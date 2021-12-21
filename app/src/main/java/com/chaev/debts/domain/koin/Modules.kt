package com.chaev.debts.domain.koin

import com.chaev.debts.data.api.RetrofitBuilder
import com.chaev.debts.domain.cicerone.CiceroneHolder
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.domain.repositories.HttpExceptionHandler
import com.chaev.debts.ui.add.AddFriendPresenter
import com.chaev.debts.ui.create.CreatePresenter
import com.chaev.debts.ui.debts.DebtsPresenter
import com.chaev.debts.ui.friendRequest.FriendRequestPresenter
import com.chaev.debts.ui.friends.FriendsPresenter
import com.chaev.debts.ui.login.LoginPresenter
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
    single { DebtsApiRepository(RetrofitBuilder.apiService) }
    single { CiceroneHolder() }
    single { HttpExceptionHandler(get<DebtsApiRepository>(), get<CiceroneHolder>().router) }
}