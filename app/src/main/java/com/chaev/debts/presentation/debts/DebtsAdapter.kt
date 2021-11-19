package com.chaev.debts.presentation.debts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaev.debts.databinding.DebtItemBinding
import com.chaev.debts.domain.models.Debt

class DebtsAdapter(var debts: List<Debt>) : RecyclerView.Adapter<DebtsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: DebtItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(debt: Debt) {
            binding.moneyView.text = debt.money
            binding.senderView.text = debt.creditor
            binding.receiverView.text = debt.debtor
            binding.createdView.text = debt.created
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DebtItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val debt = debts[position]
        holder.bind(debt)
    }

    override fun getItemCount(): Int = debts.size
}