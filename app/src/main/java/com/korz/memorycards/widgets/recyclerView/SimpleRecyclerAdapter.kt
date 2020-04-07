package com.korz.memorycards.widgets.recyclerView

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.korz.memorycards.BR


open class SimpleRecyclerAdapter<T>(
    val layoutId: Int = 0,
    val listener: ClickListener<T>? = null,
    val compareObject: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    val compareContent: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    val filterContent: (String, T) -> Boolean = { _, _ -> true }
): RecyclerAdapter<T>() {

    open var clickListener =
        ClickListenerWrapper.wrap(listener)

    constructor(
        layoutId: Int,
        l: (View, T, Int) -> Unit,
        compareContent: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
        compareObject: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
        filterContent: (String, T) -> Boolean = { _, _ -> true }
    ): this(
        layoutId,
        object: ClickListener<T> {
            override fun onClick(view: View, item: T, position: Int) {
                l(view, item, position)
            }
        },
        compareObject = compareObject,
        compareContent = compareContent,
        filterContent = filterContent
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        // set the view id as the item position
        holder.itemView.setId(position)
    }

    override fun getLayoutId(position: Int): Int {
        return layoutId
    }

    override fun getArguments(position: Int): SparseArray<Any> {
        val item = getItem(position)
        val arguments = SparseArray<Any>()
        arguments.put(BR.item, item)
        arguments.put(BR.action, clickListener)
        arguments.put(BR.position, position)
        arguments.put(BR.itemsCount, items.size)
        return arguments
    }

    /**
     * Removes item from the adapter by item position
     */
    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun add(item: T) {
        items.add(item)
        notifyDataSetChanged()
    }

    open fun setDifferenceItems(newItems: List<T>) {
        val diffResult = DiffUtil.calculateDiff(
            CommonDiffUtilCallback(
                items,
                newItems,
                compareContent,
                compareObject
            ), true
        )
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    fun replaceItem(item: T) {
        for(i in 0 until items.size) {
            if(compareObject(items[i], item)) {
                items[i] = item
                notifyItemChanged(i)
                return
            }
        }
    }

    fun updateAll(predicate: (T) -> Boolean, mapper: (T) -> T) {
        for(i in 0 until items.size) {
            val oldValue = items[i]
            if(predicate(oldValue)) {
                items[i] = mapper(oldValue)
            }
        }
        notifyDataSetChanged()
    }

    /**
     * On item view click listener
     */
    interface ClickListener<T> {
        fun onClick(view: View, item: T, position: Int)
    }
}
