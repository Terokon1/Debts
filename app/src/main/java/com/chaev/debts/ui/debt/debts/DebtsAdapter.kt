package com.chaev.debts.ui.debt.debts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaev.debts.databinding.ItemDebtBinding
import com.chaev.debts.domain.models.Debt

class DebtsAdapter : RecyclerView.Adapter<DebtsAdapter.ViewHolder>() {
    var debts: List<Debt> = emptyList()
    class ViewHolder(private val binding: ItemDebtBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(debt: Debt) {
            binding.moneyView.text = debt.money
            binding.senderView.text = debt.creditor.username
            binding.receiverView.text = debt.debtor.username
            binding.createdView.text = debt.created
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDebtBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val debt = debts[position]
        holder.bind(debt)
    }

    override fun getItemCount(): Int = debts.size
}