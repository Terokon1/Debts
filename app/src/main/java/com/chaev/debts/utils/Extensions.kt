package com.chaev.debts.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager



fun View.hideKeyboard(ctx: Context? = context) {
    (ctx?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}