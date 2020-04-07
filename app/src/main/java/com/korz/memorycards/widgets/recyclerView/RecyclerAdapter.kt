package com.korz.memorycards.widgets.recyclerView

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView adapter with DataBinding support
 */

abstract class RecyclerAdapter<T>: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var items = mutableListOf<T>()
        set(list) {
            field = list
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int {
        return items.size
    }

    open fun getItem(position: Int): T? {
        return if(0 <= position && position < items.size) items[position] else null
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        prepareView(binding.root)
        return ViewHolder(binding)
    }

    open fun prepareView(root: View) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getArguments(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position)
    }

    /**
     * @return item view layout id
     */
    abstract fun getLayoutId(position: Int): Int

    /**
     * @return arguments passed into xml as variables
     */
    abstract fun getArguments(position: Int): SparseArray<Any>


    /**
     * Item view holder
     */
    open class ViewHolder(protected val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(arguments: SparseArray<Any>) {
            for(i in 0 until arguments.size()) {
                binding.setVariable(arguments.keyAt(i), arguments.valueAt(i))
            }
            binding.executePendingBindings()
        }
    }
}
