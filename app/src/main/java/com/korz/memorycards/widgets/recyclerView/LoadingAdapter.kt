package com.korz.memorycards.widgets.recyclerView

import android.view.View
import com.korz.memorycards.R

open class LoadingAdapter<T>(
    layoutId: Int = 0,
    listener: ClickListener<T>? = null,
    compareObject: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    compareContent: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    filterContent: (String, T) -> Boolean = { _, _ -> true }
) : SimpleRecyclerAdapter<T>(
    layoutId,
    listener,
    compareObject,
    compareContent,
    filterContent
) {

    constructor(
        layoutId: Int,
        l: (View, T, Int) -> Unit,
        compareContent: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
        compareObject: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
        filterContent: (String, T) -> Boolean = { _, _ -> true }
    ) : this(
        layoutId,
        object : ClickListener<T> {
            override fun onClick(view: View, item: T, position: Int) {
                l(view, item, position)
            }
        },
        compareObject = compareObject,
        compareContent = compareContent,
        filterContent = filterContent
    )

    private var offset: Int = 0
    var isLoading: Boolean
        get() {
            return offset == 1
        }
        set(value) {
            offset = if (value) 1 else 0
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return super.getItemCount() + offset
    }

    override fun getLayoutId(position: Int): Int {
        return if (position < items.size) layoutId else R.layout.item_loading_list
    }

}