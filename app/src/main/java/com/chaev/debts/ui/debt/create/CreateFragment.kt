package com.chaev.debts.ui.debt.create


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chaev.debts.databinding.FragmentCreateBinding
import com.chaev.debts.ui.base.INavigationDisabled
import com.chaev.debts.ui.debt.create.bottomSheet.FriendsBottomSheet
import org.koin.android.ext.android.inject

class CreateFragment : Fragment(), INavigationDisabled {
    private lateinit var binding: FragmentCreateBinding
    private val presenter: CreatePresenter by inject()
    var creditorMode = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = FriendsBottomSheet()
        presenter.attachView(this)

        binding.arrowView.setOnClickListener {
            if (creditorMode) {
                binding.arrowView.text = "<-"
                binding.username1.visibility = View.VISIBLE
                binding.username2.visibility = View.GONE
                creditorMode = false
            } else {
                binding.arrowView.text = "->"
                binding.username1.visibility = View.GONE
                binding.username2.visibility = View.VISIBLE
                creditorMode = true
            }
        }
        binding.createDebtButton.setOnClickListener {

            val money = binding.inputMoney.text.toString()
            val description = binding.inputDescription.text.toString()
        }
        binding.chooseButton1.setOnClickListener {

            bottomSheet.show(childFragmentManager, FriendsBottomSheet.TAG)
        }
        binding.chooseButton2.setOnClickListener {

            bottomSheet.show(childFragmentManager, FriendsBottomSheet.TAG)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }


}