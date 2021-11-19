package com.chaev.debts.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chaev.debts.App
import com.chaev.debts.databinding.FragmentDebtsBinding
import com.chaev.debts.databinding.FragmentLoginBinding
import com.chaev.debts.presentation.debts.DebtsAdapter
import com.chaev.debts.presentation.debts.DebtsPresenter
import java.util.zip.Inflater

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var presenter: LoginPresenter? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = (requireActivity().application as App).loginPresenter
    }

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
        presenter?.attachView(this)
        binding.loginButton.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val password = binding.inputPassword.text.toString()
            presenter?.onLoginClicked(username, password, requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.detachView()
    }
}