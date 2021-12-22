package com.chaev.debts.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import com.chaev.debts.R
import com.chaev.debts.Screens
import com.chaev.debts.databinding.ActivityMainBinding
import com.chaev.debts.domain.cicerone.CiceroneHolder
import com.chaev.debts.ui.base.IFragmentHolder
import com.chaev.debts.ui.meeting.login.LoginFragment
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), IFragmentHolder {
    private val cicerone: CiceroneHolder by inject()
    private val navigatorHolder = cicerone.navigatorHolder
    private val navigator = AppNavigator(this, R.id.fragment_container, supportFragmentManager)
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
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, LoginFragment())
                .commit()
            binding.barLayout.visibility = View.INVISIBLE
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        }
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    cicerone.router.replaceScreen(Screens.Debts())
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

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onAttach(fragment: Fragment) {
        onFragmentChanged(fragment)
    }

    private fun onFragmentChanged(fragment: Fragment) {
        binding.drawerLayout.setDrawerLockMode(if (fragment is LoginFragment) LOCK_MODE_LOCKED_CLOSED else LOCK_MODE_UNLOCKED)
        binding.barLayout.visibility =
            if (fragment is LoginFragment) View.INVISIBLE else View.VISIBLE

    }
}