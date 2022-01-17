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
        binding.username1.text = prefs.getString(AppConsts.USERNAME_KEY, "")
        binding.arrowView.setOnClickListener {
            if (creditorMode) {
                binding.arrowView.text = "<-"
                binding.username1.visibility = View.GONE
                binding.chooseButton1.visibility = View.VISIBLE
                binding.username2.visibility = View.VISIBLE
                binding.username2.text = prefs.getString(AppConsts.USERNAME_KEY, "")
                binding.chooseButton2.visibility = View.GONE
                creditorMode = false
            } else {
                binding.arrowView.text = "->"
                binding.username1.visibility = View.VISIBLE
                binding.username1.text = prefs.getString(AppConsts.USERNAME_KEY, "")
                binding.chooseButton1.visibility = View.GONE
                binding.username2.visibility = View.GONE
                binding.chooseButton2.visibility = View.VISIBLE
                creditorMode = true
            }

        }
        binding.createDebtButton.setOnClickListener {

            val money = binding.inputMoney.text.toString()
            val description = binding.inputDescription.text.toString()
            val creditorUsername = binding.username1.text.toString()
            val debtorUsername = binding.username2.text.toString()
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

        binding.chooseButton1.setOnClickListener {

            bottomSheet.show(childFragmentManager, FriendsBottomSheet.TAG)
        }
        binding.chooseButton2.setOnClickListener {

            bottomSheet.show(childFragmentManager, FriendsBottomSheet.TAG)
        }
        var result: Friend
        childFragmentManager.setFragmentResultListener("requestKey", this) { requestKey, bundle ->
            result = bundle.getParcelable<Friend>(requestKey)!!
            if (creditorMode) {
                binding.chooseButton2.visibility = View.GONE
                binding.username2.visibility = View.VISIBLE
                binding.username2.text = result.username
            } else {
                binding.chooseButton1.visibility = View.GONE
                binding.username1.visibility = View.VISIBLE
                binding.username1.text = result.username
            }
            presenter.result = result
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