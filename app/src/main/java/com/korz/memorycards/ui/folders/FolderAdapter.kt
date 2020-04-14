package com.korz.memorycards.ui.folders

import android.util.SparseArray
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.korz.memorycards.BR
import com.korz.memorycards.R
import com.korz.memorycards.interfaces.Folder
import com.korz.memorycards.widgets.recyclerView.SimpleRecyclerAdapter

class FolderAdapter(
    l: (View, Folder, Int) -> Unit,
    val layoutCallback: LayoutCallback
) : SimpleRecyclerAdapter<Folder>(
    R.layout.item_folder,
    l
) {
    override fun getArguments(position: Int): SparseArray<Any> {
        return super.getArguments(position).also {

            //it.put(BR.layoutCallback, object : LayoutCallback)
        }
    }
}

interface LayoutCallback {
    fun onLayoutChange(layout: MotionLayout)
}