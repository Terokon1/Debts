package com.chaev.debts.presentation.debts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaev.debts.App
import com.chaev.debts.databinding.FragmentDebtsBinding
import com.chaev.debts.domain.models.Debt

class DebtsFragment : Fragment() {
    private lateinit var binding: FragmentDebtsBinding
    private var presenter: DebtsPresenter? = null
    private var adapter = DebtsAdapter(listOf())
    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = (requireActivity().application as App).debtsPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebtsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.attachView(this)
        presenter?.collectDebts()
        binding.debtsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.debtsRecycler.adapter = adapter
        binding.createButton.setOnClickListener { presenter?.onCreateClicked() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.detachView()
    }

    fun fillRecycler(debts: List<Debt>) {
        adapter.debts = debts
        adapter.notifyDataSetChanged()
    }

}