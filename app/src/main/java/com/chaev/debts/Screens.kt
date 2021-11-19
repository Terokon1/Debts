package com.chaev.debts

import com.chaev.debts.presentation.create.CreateFragment
import com.chaev.debts.presentation.debts.DebtsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Debts() = FragmentScreen { DebtsFragment() }
    fun Create() = FragmentScreen { CreateFragment() }

}