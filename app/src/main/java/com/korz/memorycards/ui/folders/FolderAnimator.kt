package com.korz.memorycards.ui.folders

import android.transition.TransitionManager
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.korz.memorycards.R

class FolderAnimator() : SimpleItemAnimator(){
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        return true
    }

    override fun runPendingAnimations() {

    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        return true
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
        val oldLayout = oldHolder?.itemView as ConstraintLayout?
        val newLayout = newHolder?.itemView as ConstraintLayout?
        val constraintSet = ConstraintSet()
        constraintSet.clone(newLayout)
        TransitionManager.beginDelayedTransition(oldLayout)
        constraintSet.applyTo(oldLayout)
        return true
    }

    override fun isRunning(): Boolean {
        return true
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {

    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        return true
    }

    override fun endAnimations() {

    }

}