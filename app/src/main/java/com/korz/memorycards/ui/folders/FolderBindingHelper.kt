package com.korz.memorycards.ui.folders

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.korz.memorycards.R
import com.korz.memorycards.interfaces.Folder
import com.korz.memorycards.widgets.recyclerView.SimpleRecyclerAdapter

object FolderBindingHelper {

    @JvmStatic
    @BindingAdapter(value = ["childFolders", "childListener"])
    fun setChildFolders(
        view: RecyclerView,
        list: List<Folder>?,
        clickListener: SimpleRecyclerAdapter.ClickListener<Folder>
    ) {
        val adapter = SimpleRecyclerAdapter<Folder>(
            layoutId = R.layout.item_child_folder,
            l = { view, item, position ->
                clickListener.onClick(view, item, position)
            })
    }
}