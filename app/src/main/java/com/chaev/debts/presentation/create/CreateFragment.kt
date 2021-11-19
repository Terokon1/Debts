package com.chaev.debts.presentation.create

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chaev.debts.App
import com.chaev.debts.databinding.FragmentCreateBinding

class CreateFragment:Fragment() {
    private lateinit var binding: FragmentCreateBinding
    private var presenter: CreatePresenter? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = (requireActivity().application as App).createPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.attachView(this)
        binding.createDebtButton.setOnClickListener {
            val creditor = binding.inputCreditor.text.toString()
            val debtor = binding.inputDebtor.text.toString()
            val money = binding.inputMoney.text.toString()
            val description = binding.inputDescription.text.toString()
            presenter?.postDebt(creditor, debtor, money, description)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.detachView()
    }
}