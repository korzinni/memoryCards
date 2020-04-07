package com.korz.memorycards.widgets.recyclerView

import android.util.SparseArray
import android.view.View
import com.korz.memorycards.BR


open class MultiChoiceAdapter<T>(
    layoutId: Int,
    listener: ClickListener<T>? = null,
    selectCallback: ((List<T>) -> Unit)? = null,
    compareObject: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    compareContent: (T, T) -> Boolean = { t1, t2 -> t1 == t2 }
): SimpleRecyclerAdapter<T>(layoutId, compareObject = compareObject, compareContent = compareContent) {

    val selectedData = HashSet<T>()
    val preselectData = mutableListOf<T>()

    override var clickListener =
        ClickListenerWrapper.wrap(object :
            ClickListener<T> {
            override fun onClick(view: View, item: T, position: Int) {
                if (selectedData.containObject(item)) {
                    selectedData.remove(item)
                } else {
                    selectedData.add(item)
                }
                notifyItemChanged(position)
                listener?.onClick(view, item, position)
                selectCallback?.invoke(getSelectedItems())
            }

        }, 0)

    override fun getArguments(position: Int): SparseArray<Any> {
        val item = getItem(position)
        val arguments = super.getArguments(position)
        arguments.put(BR.isSelected, isSelectedItem(position))
        return arguments
    }

    fun isSelectedItem(position: Int): Boolean {
        return selectedData.containObject(items[position])
    }

    fun getSelectedItems(): List<T> {
        return selectedData.toList()
    }

    fun init(data: List<T>, selectedData: List<T>?) {
        items = data.toMutableList()
        initSelection(selectedData)
    }

    fun initSelection(selectedData: List<T>?) {
        this.selectedData.clear()

        if(selectedData.isNullOrEmpty()) {
            notifyDataSetChanged()
            return
        }

        if(items.isEmpty()) {
            preselectData.clear()
            preselectData.addAll(selectedData)
        } else {
            items.forEachIndexed { dataIndex, dataItem ->
                selectedData.forEach {
                    if(compareContent.invoke(dataItem, it)) {
                        this.selectedData.add(dataItem)
                    }
                }
            }
            notifyDataSetChanged()

        }

    }

    override fun setDifferenceItems(newItems: List<T>) {
        super.setDifferenceItems(newItems)
        if(preselectData.isNotEmpty()) {
            initSelection(preselectData)
            preselectData.clear()
        }
    }

    fun HashSet<T>.containObject(t: T): Boolean {
        forEach {
            if(compareObject.invoke(it, t)) {
                return true
            }
        }
        return false
    }
}