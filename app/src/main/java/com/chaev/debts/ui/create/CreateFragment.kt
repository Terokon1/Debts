package com.chaev.debts.ui.create

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chaev.debts.App
import com.chaev.debts.databinding.FragmentCreateBinding
import org.koin.android.ext.android.inject

class CreateFragment : Fragment() {
    private lateinit var binding: FragmentCreateBinding
    private val presenter: CreatePresenter by inject()
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
        presenter.attachView(this)
        binding.createDebtButton.setOnClickListener {
            val creditor = binding.inputCreditor.text.toString()
            val debtor = binding.inputDebtor.text.toString()
            val money = binding.inputMoney.text.toString()
            val description = binding.inputDescription.text.toString()
            presenter.postDebt(creditor, debtor, money, description)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }


}