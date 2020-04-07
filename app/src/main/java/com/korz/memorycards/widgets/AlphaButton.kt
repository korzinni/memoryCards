package ru.id_east.gm.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.GestureDetectorCompat


class AlphaButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        setOnClickListener { }
    }

    val gestureListener = GestureListener()

    val detectorCompat = GestureDetectorCompat(context, gestureListener)

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        //alpha = if (!enabled) 0.32f else 1f
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled)
            return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                alpha = 0.54f
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                alpha = 1f
            }
        }
        return if (detectorCompat.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onContextClick(e: MotionEvent?): Boolean {
            performClick()
            return false
        }
    }


}