package com.korz.memorycards.widgets.recyclerView

import android.view.View

class InfinityRecyclerAdapter<T>(
    layoutId: Int,
    listener: ClickListener<T>? = null
): SimpleRecyclerAdapter<T>(layoutId, listener) {

    constructor(
        layoutId: Int,
        l: (View, T, Int) -> Unit
    ): this(layoutId, object:
        ClickListener<T> {
        override fun onClick(view: View, item: T, position: Int) {
            l(view, item, position)
        }
    })

    override fun getItemCount(): Int {
        return if(items.isEmpty()) 0 else Int.MAX_VALUE
    }

    override fun getItem(position: Int): T? {
        return when(position) {
            in 0 until items.size -> items[position]
            else -> items[position % items.size]
        }
    }
}