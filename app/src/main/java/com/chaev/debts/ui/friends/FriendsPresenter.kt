package com.chaev.debts.ui.friends

import android.util.Log
import com.chaev.debts.App
import com.chaev.debts.Screens
import com.chaev.debts.domain.models.Friend
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.ui.debts.DebtsFragment
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendsPresenter(private val router: Router, private val debtsApiRepository: DebtsApiRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: FriendsFragment? = null
    fun attachView(fragment: FriendsFragment) {
        view = fragment
    }

    fun detachView() {
        view = null
    }

    fun collectFriends(){
        scope.launch {
            val friends = getFriends()
            withContext(Dispatchers.Main){view?.fillRecycler(friends)}
        }
    }
    fun navigateRequests(){
        router.navigateTo(Screens.FriendRequest())
    }
    fun navigateAddFriend(){
        router.navigateTo(Screens.AddFriend())
    }

    private suspend fun getFriends() : List<Friend> =
        when (val r = debtsApiRepository.getFriends()) {
            is Right -> {
                Log.d("Debug", "${r.value}")
                r.value
            }
            is Left -> {
                Log.d("Debug", "No friend", r.value)
                listOf()
            }
        }
    }

