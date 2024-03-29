package com.chaev.debts.ui.friend.friendRequest

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaev.debts.databinding.FragmentFriendRequestBinding
import com.chaev.debts.domain.models.FriendRequest
import com.chaev.debts.ui.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class FriendRequestFragment : BaseFragment() {
    lateinit var binding: FragmentFriendRequestBinding
    private val presenter: FriendRequestPresenter by inject()
    private val adapter = FriendRequestAdapter { id, status ->
        presenter.responseButtonsClickListener(id, status)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        binding.friendRequestsList.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.friendRequestsList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    suspend fun showPatchError() {
        withContext(Dispatchers.Main) {
//            Toast.makeText(context, "Something went wrong :(", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateRecycler(requests: List<FriendRequest>) {
        adapter.requests = requests
        adapter.notifyDataSetChanged()
    }
}