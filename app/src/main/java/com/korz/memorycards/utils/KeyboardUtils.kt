package ru.id_east.gm.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun showKeyboard(view: View) {
        val manager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED)

    }

    fun hideKeyboard(view: View) {
        val manager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, 0)

    }

    fun showKeyboardWithDelay(view: View, delay: Long = 200) {
        view.postDelayed({
            view.requestFocus()
            KeyboardUtils.showKeyboard(view)
        }, delay)
    }

    fun hideKeyboardWithDelay(view: View, delay: Long = 200) {
        view.postDelayed({
            view.requestFocus()
            KeyboardUtils.hideKeyboard(view)
        }, delay)
    }
}