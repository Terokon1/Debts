package com.chaev.debts.ui.debt.create.bottomSheet

import android.util.Log
import com.chaev.debts.data.exceptionsHandlers.HttpExceptionHandler
import com.chaev.debts.domain.models.Friend
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.ui.friend.friends.FriendsFragment
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomSheetPresenter(
    private val debtsApiRepository: DebtsApiRepository,
    private val handler: HttpExceptionHandler
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: FriendsBottomSheet? = null
    private var items = emptyList<Friend>()

    init {
        scope.launch {
            items = getFriends()
            withContext(Dispatchers.Main) { view?.updateRecycler(items) }
        }
    }

    fun attachView(fragment: FriendsBottomSheet) {
        view = fragment
        view?.updateRecycler(items)
    }

    fun detachView() {
        view = null
    }

    fun fillRecycler() {
        scope.launch {
            items = getFriends()
            withContext(Dispatchers.Main) { view?.updateRecycler(items) }
        }
    }

    private suspend fun getFriends(): List<Friend> =
        when (val r = handler.runWithAuthRetry(debtsApiRepository::getFriends)) {
            is Right -> {
                Log.d("Debug", "${r.value}")
                r.value
            }
            is Left -> {
                Log.d("Debug", "No friend", r.value)
                listOf()
            }
        }

    fun selectFriend() {

    }

}