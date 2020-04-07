package com.korz.memorycards.widgets.recyclerView

import android.os.SystemClock
import android.view.View
import com.korz.memorycards.BuildConfig


class ClickListenerWrapper<T> private constructor(
    private val clickListener: SimpleRecyclerAdapter.ClickListener<T>, private val clickDelay: Long
): SimpleRecyclerAdapter.ClickListener<T> {
    private var lastClickTime: Long = 0

    override fun onClick(view: View, item: T, position: Int) {
        if(SystemClock.elapsedRealtime() - lastClickTime >= clickDelay) {
            clickListener.onClick(view, item, position)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    }

    companion object {

        fun <T> wrap(clickListener: SimpleRecyclerAdapter.ClickListener<T>?): SimpleRecyclerAdapter.ClickListener<T>? {
            return wrap(
                clickListener,
                BuildConfig.RECYCLER_ITEM_CLICK_DELAY
            )
        }

        fun <T> wrap(
            clickListener: SimpleRecyclerAdapter.ClickListener<T>?,
            clickDelay: Long
        ): SimpleRecyclerAdapter.ClickListener<T>? {
            return if(clickListener == null) null else ClickListenerWrapper(
                clickListener,
                clickDelay
            )
        }
    }
}