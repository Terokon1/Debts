package com.chaev.debts.ui.debt.create.bottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaev.debts.databinding.ItemFriendsBottomSheetBinding
import com.chaev.debts.domain.models.Friend
import com.chaev.debts.domain.models.base.User

class BottomSheetAdapter(private val selectFriend: (friend: Friend) -> Unit) :
    RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {
    var items: List<Friend> = emptyList()

    class ViewHolder(
        val binding: ItemFriendsBottomSheetBinding,
        private val selectFriend: (friend: Friend) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Friend) {
            binding.username.text = item.username
            binding.root.setOnClickListener {
                selectFriend(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendsBottomSheetBinding.inflate(inflater, parent, false)
        return ViewHolder(binding,selectFriend)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}