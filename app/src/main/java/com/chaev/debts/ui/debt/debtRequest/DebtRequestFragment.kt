package com.chaev.debts.ui.debt.debtRequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaev.debts.databinding.FragmentDebtRequestBinding
import com.chaev.debts.domain.models.debt.DebtRequest
import com.chaev.debts.ui.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class DebtRequestFragment : BaseFragment() {
    private lateinit var binding: FragmentDebtRequestBinding
    private val presenter: DebtRequestPresenter by inject()
    private val adapter =
        DebtRequestAdapter { id, status -> presenter.onResponseClicked(id, status) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebtRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        binding.debtRequestsList.layoutManager = LinearLayoutManager(context)
        binding.debtRequestsList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    fun updateRecycler(items: List<DebtRequest>) {
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    suspend fun showPatchError() {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Something went wrong :(", Toast.LENGTH_SHORT).show()
        }
    }
}