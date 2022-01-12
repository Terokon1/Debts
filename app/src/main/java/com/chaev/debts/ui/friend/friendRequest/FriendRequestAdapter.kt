package com.chaev.debts.ui.friend.friendRequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaev.debts.databinding.ItemFriendRequestBinding
import com.chaev.debts.domain.models.FriendRequest
import com.chaev.debts.domain.models.RequestStatus

class FriendRequestAdapter(
    private val responseButtonsClickListener: (id: String, status: RequestStatus) -> Unit
) :
    RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>() {

    var requests: List<FriendRequest> = emptyList()

    class ViewHolder(
        private val binding: ItemFriendRequestBinding,
        val responseButtonsClickListener: (id: String, status: RequestStatus) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(request: FriendRequest) {
            binding.username.text = request.user.username
            binding.accept.setOnClickListener {
                responseButtonsClickListener(
                    request.id,
                    RequestStatus.Accept
                )
            }
            binding.decline.setOnClickListener {
                responseButtonsClickListener(
                    request.id,
                    RequestStatus.Decline
                )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendRequestBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, responseButtonsClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(requests[position])
    }

    override fun getItemCount(): Int {
        return requests.size
    }
}