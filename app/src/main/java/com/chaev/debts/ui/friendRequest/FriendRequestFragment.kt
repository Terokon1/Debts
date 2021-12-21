package com.chaev.debts.ui.friendRequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaev.debts.databinding.FragmentFriendRequestBinding
import com.chaev.debts.domain.models.FriendRequest
import org.koin.android.ext.android.inject

class FriendRequestFragment : Fragment() {
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
        presenter.collectFriendRequests()
        binding.friendRequestsList.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.friendRequestsList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    fun fillRecycler(requests: List<FriendRequest>) {
        adapter.requests = requests
        adapter.notifyDataSetChanged()
    }
}