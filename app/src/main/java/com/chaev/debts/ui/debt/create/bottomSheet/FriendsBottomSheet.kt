package com.chaev.debts.ui.debt.create.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaev.debts.databinding.FragmentFriendsSheetBinding
import com.chaev.debts.domain.models.Friend
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class FriendsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFriendsSheetBinding
    private val presenter: BottomSheetPresenter by inject()
    private val adapter = BottomSheetAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendsSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        binding.friendsList.layoutManager = LinearLayoutManager(context)
        binding.friendsList.adapter = adapter
        presenter.fillRecycler()
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    fun updateRecycler(friends: List<Friend>) {
        adapter.items = friends
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val TAG = "BottomSheet"
    }
}