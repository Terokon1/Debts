package com.chaev.debts.ui.meeting.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chaev.debts.databinding.FragmentLoginBinding
import com.chaev.debts.ui.base.BaseFragment
import com.chaev.debts.ui.base.INavigationDisabled
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment(), INavigationDisabled {
    private lateinit var binding: FragmentLoginBinding
    private val presenter: LoginPresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        binding.loginButton.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()
            presenter.onLoginClicked(username, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    suspend fun showAuthorizeError() = withContext(Dispatchers.Main) {
        Toast.makeText(
            context,
            "Authorization failed!",
            Toast.LENGTH_SHORT
        ).show()
    }
}