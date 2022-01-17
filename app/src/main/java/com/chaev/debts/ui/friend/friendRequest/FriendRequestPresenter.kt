package com.chaev.debts.ui.friend.friendRequest

import android.util.Log
import com.chaev.debts.data.exceptionsHandlers.HttpExceptionHandler
import com.chaev.debts.data.models.friendRequest.FriendReqPatch
import com.chaev.debts.domain.models.FriendRequest
import com.chaev.debts.domain.models.RequestStatus
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendRequestPresenter(
    private val debtsApiRepository: DebtsApiRepository,
    private val handler: HttpExceptionHandler
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: FriendRequestFragment? = null
    private var items: List<FriendRequest> = emptyList()


    init {
        scope.launch {
            items = getFriendRequests()
            withContext(Dispatchers.Main) { view?.updateRecycler(items) }
        }
    }

    fun attachView(fragment: FriendRequestFragment) {
        view = fragment
        view?.updateRecycler(items)
    }

    fun detachView() {
        view = null
    }

    fun responseButtonsClickListener(id: String, status: RequestStatus) {
        scope.launch {
            when (val r = handler.runWithAuthRetry(
                {
                    debtsApiRepository.patchFriendRequest(
                        FriendReqPatch(
                            id,
                            status.toString()
                        )
                    )
                })) {
                is Right -> {
                    items = items.filter { it.id != id }
                    withContext(Dispatchers.Main) { view?.updateRecycler(items) }
                }
                is Left -> {
                    Log.d("Debug", "Failed to patch", r.value)
                    view?.showPatchError()
                }
            }
        }
    }

    private suspend fun getFriendRequests(): List<FriendRequest> =
        when (val r = handler.runWithAuthRetry({ debtsApiRepository.getFriendRequests() })) {
            is Right -> {
                Log.d("Debug", "${r.value}")
                r.value
            }
            is Left -> {
                Log.d("Debug", "No friend requests", r.value)
                listOf()
            }
        }
}