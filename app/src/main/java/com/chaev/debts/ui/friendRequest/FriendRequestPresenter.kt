package com.chaev.debts.ui.friendRequest

import android.util.Log
import com.chaev.debts.data.models.request.FriendReqPatch
import com.chaev.debts.domain.models.FriendRequest
import com.chaev.debts.domain.models.RequestStatus
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.utils.Left
import com.chaev.debts.utils.Right
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendRequestPresenter(private val debtsApiRepository: DebtsApiRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: FriendRequestFragment? = null
    private var items: List<FriendRequest> = emptyList()
    fun attachView(fragment: FriendRequestFragment) {
        view = fragment

        view?.fillRecycler(items)
    }

    fun detachView() {
        view = null
    }

    fun collectFriendRequests() {
        scope.launch {
            items = getFriendRequests()
            withContext(Dispatchers.Main) { view?.fillRecycler(items) }
        }
    }

    fun responseButtonsClickListener(id: String, status: RequestStatus) {
        scope.launch {
            debtsApiRepository.patchFriendRequest(
                FriendReqPatch(
                    id,
                    status.toString()
                )
            )
            items = items.filter { it.id != id }
            withContext(Dispatchers.Main) { view?.fillRecycler(items) }
        }

    }

    private suspend fun getFriendRequests(): List<FriendRequest> =
        when (val r = debtsApiRepository.getFriendRequests()) {
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