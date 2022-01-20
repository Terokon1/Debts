package com.chaev.debts.ui.meeting.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chaev.debts.databinding.FragmentRegistrationBinding
import com.chaev.debts.ui.base.BaseFragment
import com.chaev.debts.ui.base.INavigationDisabled
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class RegistrationFragment : BaseFragment(), INavigationDisabled {
    private val presenter: RegistrationPresenter by inject()
    private lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registrationButton.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    presenter.register(username, password)
                } else {
                    Toast.makeText(context, "Password mismatch", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun showError() {
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Registration failed!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    suspend fun showSuccess() {
        withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "Registration success!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}