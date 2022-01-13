package com.chaev.debts.ui.friend.add

import android.util.Log
import com.chaev.debts.data.exceptionsHandlers.HttpExceptionHandler
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFriendPresenter(
    private val router: Router,
    private val repository: DebtsApiRepository,
    private val handler: HttpExceptionHandler
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: AddFriendFragment? = null
    fun onAttach(fragment: AddFriendFragment) {
        view = fragment
    }

    fun onDetach() {
        view = null
    }

    fun navigateBack() {
        router.exit()
    }

    fun addFriendClicked(username: String) {
        scope.launch {
            when (val r = handler.runWithAuthRetryArgs(repository::postFriendRequest, username)) {
                is Right -> {
                    navigateBack()
                }
                is Left -> {
                    Log.d("Debug", "Add Friend failed", r.value)
                }
            }
        }
    }
}