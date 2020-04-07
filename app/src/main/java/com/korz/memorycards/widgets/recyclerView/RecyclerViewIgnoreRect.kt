package com.korz.memorycards.widgets.recyclerView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewIgnoreRect @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var topIgnoreRect = 0f
    var bottomIgnoreRect = 0f
    var startIgnoreRect = 0f
    var endIgnoreRect = 0f

    var overlayView: View? = null
    var lockBackScroll: Boolean = false

    private fun setIgnoreArea(
        topIgnoreRect: Float,
        startIgnoreRect: Float,
        bottomIgnoreRect: Float,
        endIgnoreRect: Float
    ) {
        this.topIgnoreRect = topIgnoreRect
        this.bottomIgnoreRect = bottomIgnoreRect
        this.startIgnoreRect = startIgnoreRect
        this.endIgnoreRect = endIgnoreRect
    }

    fun setIgnoreArea(overlayView: View) {
        this.overlayView = overlayView
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (e != null) {
            overlayView?.let {
                setIgnoreArea(it.y, it.x, it.y + it.height, it.x + it.width)
            }

            lockBackScroll =
                e.x > startIgnoreRect && e.x < endIgnoreRect && e.y > topIgnoreRect && e.y < bottomIgnoreRect

        }
        return super.onTouchEvent(e)
    }

    override fun canScrollVertically(direction: Int): Boolean {
        if (direction == -1 && lockBackScroll) {
            return false
        } else {
            return super.canScrollVertically(direction)
        }
    }
}