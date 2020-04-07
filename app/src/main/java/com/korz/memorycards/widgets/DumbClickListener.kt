package com.korz.memorycards.widgets

import android.os.SystemClock
import android.util.Log
import android.view.View

class DumbClickListener(
    val timeDelay: Int = 500, val listener: (View) -> Unit
): View.OnClickListener {
    private var lastClickTime: Long = 0

    override fun onClick(v: View?) {
        if(SystemClock.elapsedRealtime() - lastClickTime >= timeDelay) {
            listener.invoke(v!!)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    }

}