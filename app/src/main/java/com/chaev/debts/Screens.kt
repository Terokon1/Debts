package com.chaev.debts

import com.chaev.debts.ui.friend.add.AddFriendFragment
import com.chaev.debts.ui.debt.create.CreateFragment
import com.chaev.debts.ui.debt.debts.DebtsFragment
import com.chaev.debts.ui.debt.pager.DebtsPagerFragment
import com.chaev.debts.ui.friend.friendRequest.FriendRequestFragment
import com.chaev.debts.ui.friend.friends.FriendsFragment
import com.chaev.debts.ui.meeting.login.LoginFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Debts() = FragmentScreen { DebtsFragment() }
    fun Create() = FragmentScreen { CreateFragment() }
    fun Friends() = FragmentScreen { FriendsFragment() }
    fun FriendRequest() = FragmentScreen { FriendRequestFragment() }
    fun AddFriend() = FragmentScreen { AddFriendFragment() }
    fun Login() = FragmentScreen { LoginFragment() }
    fun DebtsPager() = FragmentScreen { DebtsPagerFragment() }
}