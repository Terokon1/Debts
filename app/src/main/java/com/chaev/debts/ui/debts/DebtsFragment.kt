package com.chaev.debts.ui.debts

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
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject

class DebtsFragment : Fragment() {
    private lateinit var binding: FragmentDebtsBinding
private val presenter: DebtsPresenter by inject()
    private var adapter = DebtsAdapter(listOf())

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
        presenter.attachView(this)
        binding.debtsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.debtsRecycler.adapter = adapter
        binding.createButton.setOnClickListener { presenter.onCreateClicked() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    fun fillRecycler(debts: List<Debt>) {
        adapter.debts = debts
        adapter.notifyDataSetChanged()
    }

}