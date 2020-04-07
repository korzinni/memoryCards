package com.korz.memorycards.widgets.recyclerView

import androidx.recyclerview.widget.DiffUtil

class CommonDiffUtilCallback<T>(
    protected val oldList: List<T>,
    protected val newList: List<T>,
    val compareContent: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    val compareObject: (T, T) -> Boolean = { t1, t2 -> t1 == t2 }
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldInstance = oldList[oldItemPosition]
        val newInstance = newList[newItemPosition]
        return if(oldInstance != null) compareObject.invoke(oldInstance, newInstance) else newInstance == null
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldInstance = oldList[oldItemPosition]
        val newInstance = newList[newItemPosition]
        return if(oldInstance != null) compareContent.invoke(oldInstance, newInstance) else newInstance == null
    }

}
