package ru.id_east.gm.ui.common

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment


abstract class BaseDialog<T : ViewDataBinding> : DialogFragment() {
    open val isBackgroundVisible: Boolean = true
    open val widthRatio: Float = 0.8f
    open val fullscreen: Boolean = false
    open val softInputMode: Int = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    abstract val layoutId: Int
    lateinit var binding: T

    override fun onResume() {
        super.onResume()
        val window = dialog?.window ?: return
        if (!isBackgroundVisible) window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        val display = window.windowManager?.defaultDisplay ?: return
        val point = Point()
        display.getSize(point)
        if (fullscreen) {
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//                window.statusBarColor = resources.getColor(R.color.input_field_hint_gray)
//            }
        } else {
            window.setLayout((point.x * widthRatio).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        window.setSoftInputMode(softInputMode)
        window.setGravity(Gravity.CENTER)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreated(binding)
    }

    /**
     *
     * call on real [onViewCreated] convenient place to init fragment logic
     * */
    abstract fun onViewCreated(binding: T)
}