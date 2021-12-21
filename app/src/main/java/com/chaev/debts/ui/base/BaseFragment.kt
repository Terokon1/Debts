package com.chaev.debts.ui.base

import android.app.Activity
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        (activity as? IFragmentHolder)?.onAttach(this)
    }
}