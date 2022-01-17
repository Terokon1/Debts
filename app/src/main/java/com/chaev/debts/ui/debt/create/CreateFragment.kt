package com.chaev.debts.ui.debt.create


import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chaev.debts.databinding.FragmentCreateBinding
import com.chaev.debts.domain.models.Friend
import com.chaev.debts.ui.base.BaseFragment
import com.chaev.debts.ui.base.INavigationDisabled
import com.chaev.debts.ui.debt.create.bottomSheet.FriendsBottomSheet
import com.chaev.debts.utils.AppConsts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class CreateFragment : BaseFragment(), INavigationDisabled {
    private lateinit var binding: FragmentCreateBinding
    private val presenter: CreatePresenter by inject()
    private val prefs: SharedPreferences by inject()
    private var creditorMode = true
    private var isSelected = false
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
        binding.creditorsUsername.text = prefs.getString(AppConsts.USERNAME_KEY, "")
        binding.arrowView.setOnClickListener {
            if (!isSelected) {
                if (creditorMode) {
                    binding.creditorsUsername.visibility = View.GONE
                    binding.chooseCreditorButton.visibility = View.VISIBLE
                    binding.debtorsUsername.visibility = View.VISIBLE
                    binding.debtorsUsername.text = prefs.getString(AppConsts.USERNAME_KEY, "")
                    binding.chooseDebtorButton.visibility = View.GONE
                    creditorMode = false
                } else {
                    binding.creditorsUsername.visibility = View.VISIBLE
                    binding.creditorsUsername.text = prefs.getString(AppConsts.USERNAME_KEY, "")
                    binding.chooseCreditorButton.visibility = View.GONE
                    binding.debtorsUsername.visibility = View.GONE
                    binding.chooseDebtorButton.visibility = View.VISIBLE
                    creditorMode = true
                }
            } else {
                if (creditorMode) {
                    binding.creditorsUsername.text = binding.debtorsUsername.text
                    binding.debtorsUsername.text = prefs.getString(AppConsts.USERNAME_KEY, "")
                    creditorMode = false
                } else {
                    binding.debtorsUsername.text = binding.creditorsUsername.text
                    binding.creditorsUsername.text = prefs.getString(AppConsts.USERNAME_KEY, "")
                    creditorMode = true
                }
            }
        }
        binding.createDebtButton.setOnClickListener {

            val money = binding.inputMoney.text.toString()
            val description = binding.inputDescription.text.toString()
            val creditorUsername = binding.creditorsUsername.text.toString()
            val debtorUsername = binding.debtorsUsername.text.toString()
            if (creditorMode) {
                if (money.isNotEmpty() && debtorUsername.isNotEmpty()) {
                    presenter.sendDebtRequest(money, description, creditorMode)
                } else {
                    Toast.makeText(context, "Something is empty", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (money.isNotEmpty() && creditorUsername.isNotEmpty()) {
                    presenter.sendDebtRequest(money, description, creditorMode)
                } else {
                    Toast.makeText(context, "Something is empty", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.chooseCreditorButton.setOnClickListener {
            bottomSheet.show(childFragmentManager, FriendsBottomSheet.TAG)
        }
        binding.chooseDebtorButton.setOnClickListener {
            bottomSheet.show(childFragmentManager, FriendsBottomSheet.TAG)
        }
        binding.creditorsUsername.setOnClickListener {
            if (isSelected) {
                if (!creditorMode) {
                    bottomSheet.show(childFragmentManager, FriendsBottomSheet.TAG)
                }
            }
        }
        binding.debtorsUsername.setOnClickListener {
            if (isSelected) {
                if (creditorMode) {
                    bottomSheet.show(childFragmentManager, FriendsBottomSheet.TAG)
                }
            }
        }
        var result: Friend
        childFragmentManager.setFragmentResultListener("requestKey", this) { requestKey, bundle ->
            result = bundle.getParcelable<Friend>(requestKey)!!
            if (creditorMode) {
                binding.chooseDebtorButton.visibility = View.GONE
                binding.debtorsUsername.visibility = View.VISIBLE
                binding.debtorsUsername.text = result.username
            } else {
                binding.chooseCreditorButton.visibility = View.GONE
                binding.creditorsUsername.visibility = View.VISIBLE
                binding.creditorsUsername.text = result.username
            }
            presenter.result = result
            isSelected = true
            Log.d("zxc", result.username)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    suspend fun showSuccessMessage() {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Debt Request sent", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun showErrorMessage() {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }


}