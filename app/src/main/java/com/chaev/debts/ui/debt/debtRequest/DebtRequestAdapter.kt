package com.chaev.debts.ui.debt.debtRequest


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaev.debts.R
import com.chaev.debts.databinding.ItemDebtRequestBinding
import com.chaev.debts.domain.models.RequestStatus
import com.chaev.debts.domain.models.debt.DebtRequest

class DebtRequestAdapter(private val onResponseClicked: (id: String, status: RequestStatus) -> Unit) :
    RecyclerView.Adapter<DebtRequestAdapter.ViewHolder>() {
    var items = emptyList<DebtRequest>()

    class ViewHolder(
        private val binding: ItemDebtRequestBinding,
        private val onResponseClicked: (id: String, status: RequestStatus) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DebtRequest) {
            if (item.isActive) {
                if (item.isYours) {
                    binding.accept.visibility = View.GONE
                    binding.decline.visibility = View.GONE
                    val text =
                        binding.info.resources.getString(
                            R.string.pending_request,
                            item.debtor.username
                        )
                    binding.info.text = text
                } else {
                    val text = binding.info.resources.getString(
                        R.string.incoming_request,
                        item.creditor.username
                    )
                    binding.info.text = text
                    binding.accept.setOnClickListener {
                        onResponseClicked(
                            item.id,
                            RequestStatus.accept
                        )
                    }
                    binding.decline.setOnClickListener {
                        onResponseClicked(
                            item.id,
                            RequestStatus.decline
                        )
                    }
                }
                binding.money.text = item.money
                binding.description.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDebtRequestBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onResponseClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}