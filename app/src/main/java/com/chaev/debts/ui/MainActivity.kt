package com.chaev.debts.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
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
import com.chaev.debts.databinding.HeaderNavigationDrawerBinding
import com.chaev.debts.domain.cicerone.CiceroneHolder
import com.chaev.debts.domain.repositories.DebtsApiRepository
import com.chaev.debts.ui.base.IBackNavigable
import com.chaev.debts.ui.base.IFragmentHolder
import com.chaev.debts.ui.base.INavigationDisabled
import com.chaev.debts.utils.*
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), IFragmentHolder {
    private val cicerone: CiceroneHolder by inject()
    private val prefs: SharedPreferences by inject()
    private val navigatorHolder = cicerone.navigatorHolder
    private val navigator = AppNavigator(this, R.id.fragment_container, supportFragmentManager)
    private val presenter: MainPresenter by inject()
    private lateinit var binding: ActivityMainBinding
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
            presenter.checkAuthorization()
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
                    binding.barLayout.visibility = View.INVISIBLE
                    binding.drawerLayout.closeDrawer(GravityCompat.START, true)
                    binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                    presenter.clearPrefs()
                    cicerone.router.replaceScreen(Screens.Login())
                    true
                }
                else -> false
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        currentFragment?.let { onFragmentChanged(it) }
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        presenter.saveTokens()
        presenter.detachView()
        super.onPause()
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

    fun performLogin() {
        val header = binding.navigationView.getHeaderView(0)
        val username = header.findViewById<TextView>(R.id.username_text_view)
        runOnUiThread { username.text = prefs.getString(AppConsts.USERNAME_KEY, "") }
    }


}