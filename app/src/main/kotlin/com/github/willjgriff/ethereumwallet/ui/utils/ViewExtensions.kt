package com.github.willjgriff.ethereumwallet.ui.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by williamgriffiths on 04/05/2017.
 */
fun View.hideSoftKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}