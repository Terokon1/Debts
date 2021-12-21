package com.chaev.debts.domain.cicerone

import com.github.terrakok.cicerone.Cicerone

class CiceroneHolder {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()
}