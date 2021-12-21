package com.chaev.debts

import com.chaev.debts.ui.add.AddFriendFragment
import com.chaev.debts.ui.create.CreateFragment
import com.chaev.debts.ui.debts.DebtsFragment
import com.chaev.debts.ui.friendRequest.FriendRequestFragment
import com.chaev.debts.ui.friends.FriendsFragment
import com.chaev.debts.ui.login.LoginFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Debts() = FragmentScreen { DebtsFragment() }
    fun Create() = FragmentScreen { CreateFragment() }
    fun Friends() = FragmentScreen { FriendsFragment() }
    fun FriendRequest() = FragmentScreen{ FriendRequestFragment() }
    fun AddFriend() = FragmentScreen{ AddFriendFragment() }
    fun Login() = FragmentScreen{ LoginFragment() }
}