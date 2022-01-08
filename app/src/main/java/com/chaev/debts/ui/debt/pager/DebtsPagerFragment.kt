package com.chaev.debts.ui.debt.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chaev.debts.databinding.FragmentDebtsPagerBinding
import com.chaev.debts.ui.base.BaseFragment
import com.chaev.debts.ui.base.IBackNavigable
import com.google.android.material.tabs.TabLayoutMediator

class DebtsPagerFragment : BaseFragment(), IBackNavigable {
    lateinit var binding: FragmentDebtsPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDebtsPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = DebtsPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "List"
                1 -> tab.text = "Requests"
            }
        }.attach()
    }

    override fun onBackPressed(): Boolean =
        if (binding.viewPager.currentItem == 0) {
            false
        } else {
            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
            true
        }
}
