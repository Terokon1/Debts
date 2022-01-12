package com.chaev.debts.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import com.chaev.debts.App
import com.chaev.debts.R
import com.chaev.debts.Screens
import com.chaev.debts.databinding.ActivityMainBinding
import com.chaev.debts.domain.cicerone.CiceroneHolder
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.ui.base.IBackNavigable
import com.chaev.debts.ui.base.IFragmentHolder
import com.chaev.debts.ui.base.INavigationDisabled
import com.chaev.debts.ui.meeting.login.LoginFragment
import com.chaev.debts.utils.*
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), IFragmentHolder {
    private val cicerone: CiceroneHolder by inject()
    private val debtsApiRepository: DebtsApiRepository by inject()
    private val prefs: SharedPreferences by inject()
    private val navigatorHolder = cicerone.navigatorHolder
    private val navigator = AppNavigator(this, R.id.fragment_container, supportFragmentManager)
    private val scope = CoroutineScope(Dispatchers.IO)
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.addOnBackStackChangedListener {
            supportFragmentManager.findFragmentById(R.id.fragment_container)?.let {
                onFragmentChanged(it)
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (savedInstanceState == null) {
            checkAuthorization()
        }
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    cicerone.router.replaceScreen(Screens.DebtsPager())
                    true
                }
                R.id.item2 -> {
                    cicerone.router.replaceScreen(Screens.Friends())
                    true
                }
                R.id.item3 -> {
                    prefs.edit().apply {
                        putString(AppConsts.REFRESH_TOKEN_KEY, "")
                    }
                    binding.barLayout.visibility = View.INVISIBLE
                    binding.drawerLayout.closeDrawer(GravityCompat.START, true)
                    binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                    prefs.edit().apply {
                        putString(AppConsts.REFRESH_TOKEN_KEY, "")
                    }
                    cicerone.router.replaceScreen(Screens.Login())
                    true
                }
                else -> false
            }
        }
//        binding.topAppBar.setNavigationOnClickListener {
//            binding.drawerLayout.openDrawer(GravityCompat.END, true)
//        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        currentFragment?.let { onFragmentChanged(it) }
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        saveTokens()
        super.onPause()
    }

    override fun onDestroy() {
        saveTokens()
        super.onDestroy()
    }

    private fun saveTokens() {
        val prefs = getSharedPreferences(AppConsts.TOKENS_KEY, MODE_PRIVATE)
        prefs.edit().apply {
            putString(AppConsts.REFRESH_TOKEN_KEY, debtsApiRepository.refreshToken)
            apply()
        }
    }

    override fun onAttach(fragment: Fragment) {
        onFragmentChanged(fragment)
    }

    private fun onFragmentChanged(fragment: Fragment) {
        binding.drawerLayout.setDrawerLockMode(if (fragment is INavigationDisabled) LOCK_MODE_LOCKED_CLOSED else LOCK_MODE_UNLOCKED)
        binding.barLayout.visibility =
            if (fragment is INavigationDisabled) View.INVISIBLE else View.VISIBLE
        this.currentFocus?.hideKeyboard()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment is IBackNavigable) {
            if (!currentFragment.onBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun checkAuthorization() {
        scope.launch {
            prefs.getString(AppConsts.REFRESH_TOKEN_KEY, "")
                ?.let { token ->
                    when (debtsApiRepository.verifyToken(token)
                    ) {
                        is Right -> {
                            debtsApiRepository.refreshToken = token
                            cicerone.router.replaceScreen(Screens.DebtsPager())
                        }
                        is Left -> {
                            prefs.edit().apply {
                                putString(AppConsts.REFRESH_TOKEN_KEY, "")
                                apply()
                            }
                            cicerone.router.replaceScreen(Screens.Login())
                        }
                    }
                }
        }
    }
}