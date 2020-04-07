package ru.id_east.gm.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.google.android.material.snackbar.Snackbar
import com.korz.memorycards.widgets.DumbClickListener
import ru.id_east.gm.utils.KeyboardUtils
import java.net.UnknownHostException

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    abstract val layoutId: Int
    lateinit var binding: T
    open val softInputMode: Int = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window?.setSoftInputMode(softInputMode)
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root


    }

    open fun onBackPressed() {
        KeyboardUtils.hideKeyboard(binding.root)
        parentFragmentManager.popBackStack()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(this) { onBackPressed() }
        onViewCreated(binding, savedInstanceState)
    }


    /**
     * call on real [onViewCreated] convenient place to init fragment logic
     * */
    abstract fun onViewCreated(binding: T, savedInstanceState: Bundle?)

    var snackbar: Snackbar? = null
//    val defaultErrorHandler = { it: Throwable ->
//        if (it is UnknownHostException) {
//            var localCopySnackBar = snackbar
//            if (localCopySnackBar == null || !localCopySnackBar.isShown) {
//                localCopySnackBar = Snackbar.make(
//                    (requireActivity() as MainActivity).binding.root, "No internet connection",
//                    Snackbar.LENGTH_LONG
//                )
//                snackbar = localCopySnackBar
//                localCopySnackBar.show()
//            }
//        } else if (BuildConfig.SHOW_ERRORS) {
//            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
//        } else {
//            Crashlytics.logException(it)
//        }
//
//
//    }

}


fun View.setDumbClickListener(timeDelay: Int = 500, callback: ((View) -> Unit)) {
    this.setOnClickListener(DumbClickListener(timeDelay, callback))
}