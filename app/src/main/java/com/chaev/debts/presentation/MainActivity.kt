package com.chaev.debts.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.chaev.debts.App
import com.chaev.debts.R
import com.chaev.debts.presentation.debts.DebtsFragment
import com.chaev.debts.presentation.login.LoginFragment
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {
    private val navigatorHolder = App.INSTANCE.navigatorHolder
    private val navigator = AppNavigator(this, R.id.fragment_container, supportFragmentManager)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, LoginFragment())
                .commit()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}