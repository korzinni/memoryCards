package ru.id_east.gm.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.crashlytics.android.Crashlytics
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.korz.memorycards.BuildConfig
import com.korz.memorycards.R


abstract class BaseBottomSheetDialog<T : ViewDataBinding>() : BottomSheetDialogFragment() {

    abstract val layoutId: Int
    lateinit var binding: T
    open val style = R.style.AppBottomSheetDialogTheme
    lateinit var behavior: BottomSheetBehavior<View>
    lateinit var behaviorView: ViewGroup
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED)

        dialog.setOnShowListener {
            onShowDialog(dialog)
        }
    }

    fun onShowDialog(dialog: Dialog) {
        behaviorView = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        behavior = BottomSheetBehavior.from(behaviorView)
        onShow(dialog, behavior)

    }

    open fun onShow(dialog: Dialog, behavior: BottomSheetBehavior<View>) {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(context!!, style)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreated(binding)
    }

    protected fun getBehaviorFromDialog(dialog: Dialog?): BottomSheetBehavior<View> {
        val d = dialog as BottomSheetDialog?
        val view =
            d?.findViewById<FrameLayout>(R.id.design_bottom_sheet)
                ?: throw IllegalStateException("can't find bottom sheet")
        return BottomSheetBehavior.from(view)
    }

    /**
     *
     * call on real [onViewCreated] convenient place to init fragment logic
     * */
    abstract fun onViewCreated(binding: T)


    val defaultErrorHandler = { it: Throwable ->
        if (BuildConfig.SHOW_ERRORS) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        } else {
            Crashlytics.logException(it)
        }

    }

//    val closeDialogErrorHandler = { it: Throwable ->
//        defaultErrorHandler(it)
//        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//    }
}

