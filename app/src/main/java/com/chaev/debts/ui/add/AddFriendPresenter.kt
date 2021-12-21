package com.chaev.debts.ui.add

import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFriendPresenter(private val router: Router, private val repository: DebtsApiRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var view: AddFriendFragment? = null
    fun onAttach(fragment: AddFriendFragment) {
        view = fragment
    }

    fun onDetach() {
        view = null
    }
    fun navigateBack(){
        router.exit()
    }

    fun addFriendClicked(username: String) {
        scope.launch {
            repository.postFriendRequest(username)
        }
    }
}