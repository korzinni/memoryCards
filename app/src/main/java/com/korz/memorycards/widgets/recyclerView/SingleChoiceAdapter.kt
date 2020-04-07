package com.korz.memorycards.widgets.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.korz.memorycards.BR

open class SingleChoiceAdapter<T>(
    layoutId: Int,
    listener: ClickListener<T>? = null,
    compareObject: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    compareContent: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
    private val lockSelection: (Int, T) -> Boolean = { position, item -> false },
    filterContent: (String, T) -> Boolean = { _, _ -> true }
) : SimpleRecyclerAdapter<T>(
    layoutId,
    listener,
    compareContent = compareContent,
    compareObject = compareObject,
    filterContent = filterContent
) {

    val selectedPosition = MutableLiveData<Int>().also { it.value = -1 }

    var preselectedItem: T? = null
    override var clickListener =
        ClickListenerWrapper.wrap(object :
            ClickListener<T> {
            override fun onClick(view: View, item: T, position: Int) {
                if (!lockSelection(position, item)) {
                    selectedPosition.value = position
                    listener?.onClick(view, item, position)
                }

            }

        }, 0)

    constructor(
        layoutId: Int,
        l: (View, T, Int) -> Unit,
        compareContent: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
        compareObject: (T, T) -> Boolean = { t1, t2 -> t1 == t2 },
        lockSelection: (Int, T) -> Boolean = { position, item -> false },
        filterContent: (String, T) -> Boolean = { _, _ -> true }
    ) : this(
        layoutId,
        object :
            ClickListener<T> {
            override fun onClick(view: View, item: T, position: Int) {
                l(view, item, position)
            }
        },
        compareContent = compareContent,
        compareObject = compareObject,
        filterContent = filterContent,
        lockSelection = lockSelection
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    open fun setSelection(selectedItem: T?) {
        if (selectedItem != null) {
            if (items.isNotEmpty()) {
                items.forEachIndexed { index, item ->
                    if (compareObject(item, selectedItem)) {
                        selectedPosition.value = index
                        return
                    }
                }
            } else {
                preselectedItem = selectedItem
                return
            }
        }
        selectedPosition.value = 0
    }

    fun getSelection(): T? {
        val selection = selectedPosition.value
        if (selection != null && selection != -1 && items.isNotEmpty())
            return items.get(selection)
        else return null
    }


    override fun setDifferenceItems(newItems: List<T>) {
        super.setDifferenceItems(newItems)
        if (preselectedItem != null) {
            setSelection(preselectedItem)
            preselectedItem = null
        }
    }

    fun setData(newItems: List<T>, preselection: Int = 0) {
        items = mutableListOf<T>().also {
            it.addAll(newItems)
            selectedPosition.value = preselection
        }
    }

    inner class ViewHolder(binding: ViewDataBinding) : RecyclerAdapter.ViewHolder(binding) {
        init {
            binding.getRoot().setTag(binding)
            selectedPosition.observeForever {
                binding.setVariable(
                    BR.selectedPosition,
                    it
                )
            }
        }
    }
}