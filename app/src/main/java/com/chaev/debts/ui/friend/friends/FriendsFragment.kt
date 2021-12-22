package com.chaev.debts.ui.friend.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaev.debts.databinding.FragmentFriendsBinding
import com.chaev.debts.domain.models.Friend
import com.chaev.debts.ui.base.BaseFragment
import org.koin.android.ext.android.inject

class FriendsFragment : BaseFragment() {
    private lateinit var binding: FragmentFriendsBinding
    private val presenter: FriendsPresenter by inject()
    private val adapter = FriendsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        binding.friendsList.adapter = adapter
        binding.friendsList.layoutManager = LinearLayoutManager(requireContext())
        binding.addButton.setOnClickListener { presenter.navigateAddFriend() }
        binding.requestButton.setOnClickListener { presenter.navigateRequests() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    fun updateRecycler(friends: List<Friend>) {
        adapter.friends = friends
        adapter.notifyDataSetChanged()
    }
}
