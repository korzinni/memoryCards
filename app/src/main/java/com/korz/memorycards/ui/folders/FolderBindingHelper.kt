package com.korz.memorycards.ui.folders

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.korz.memorycards.R
import com.korz.memorycards.interfaces.Folder
import com.korz.memorycards.widgets.recyclerView.SimpleRecyclerAdapter

object FolderBindingHelper {

    @JvmStatic
    @BindingAdapter(value = ["childFolders", "childListener"])
    fun setChildFolders(
        recyclerView: RecyclerView,
        list: List<Folder>?,
        clickListener: SimpleRecyclerAdapter.ClickListener<Folder>?
    ) {

        if (list == null && clickListener == null) {
            return
        }
        val adapter = SimpleRecyclerAdapter<Folder>(
            layoutId = R.layout.item_folder
        )
        recyclerView.setHasFixedSize(true)
        recyclerView.setHasTransientState(true)
        adapter.clickListener = object : SimpleRecyclerAdapter.ClickListener<Folder> {
            override fun onClick(view: View, item: Folder, position: Int) {
                val motionLayout = view as MotionLayout

                val progress = motionLayout.progress
                val target = 1 - progress
                val animator = ValueAnimator.ofFloat(progress, target)
                animator.addUpdateListener {
                    motionLayout.progress = it.animatedValue as Float
                }
                animator.start()

//                val innerRecyclerView = view.findViewById<RecyclerView>(R.id.childFoldersList)
//                innerRecyclerView.visibility = VISIBLE
                clickListener?.onClick(view, item, position)
            }
        }
        adapter.setDifferenceItems(list ?: emptyList())
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("dependentMargin")
    fun setMarginByDeep(
        view: View,
        deep: Int?
    ) {

        if (deep == null) {
            return
        }

        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        lp.marginStart = (deep + 1) * 20
    }

    @JvmStatic
    @BindingAdapter("progress")
    fun setProgress(
        view: MotionLayout,
        progress: Float?
    ) {

        if (progress == null) {
            return
        }
        view.progress = progress
    }


}