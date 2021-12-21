package com.chaev.debts.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chaev.debts.databinding.FragmentAddFriendBinding
import org.koin.android.ext.android.inject

class AddFriendFragment : Fragment() {
    private lateinit var binding: FragmentAddFriendBinding
    private val presenter: AddFriendPresenter by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFriendBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        binding.addButton.setOnClickListener {
            val username = binding.username.text.toString()
            presenter.addFriendClicked(username)
//            presenter.navigateBack()
            Toast.makeText(context, "Friend request sent", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetach()
    }
}