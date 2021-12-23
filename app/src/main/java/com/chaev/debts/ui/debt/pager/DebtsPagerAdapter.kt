package com.chaev.debts.ui.debt.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chaev.debts.ui.base.BaseFragment
import com.chaev.debts.ui.debt.debtRequest.DebtRequestFragment
import com.chaev.debts.ui.debt.debts.DebtsFragment

class DebtsPagerAdapter(fragment: BaseFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment =
        if (position == 0) {
            DebtsFragment()
        } else {
            DebtRequestFragment()
        }
}


