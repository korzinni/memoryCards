package ru.id_east.gm.utils

import android.app.Activity
import android.content.Context
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import ru.id_east.gm.ui.common.CommonAlert

fun simpleCheckPermission(
    activity: Activity,
    permission: String,
    permissionTitle: () -> String?,
    permissionRationale: () -> String?,
    deniedCallback: (PermissionDeniedResponse?) -> Unit,
    grantedCallback: (PermissionGrantedResponse?) -> Unit
) {
    Dexter
        .withActivity(activity)
        .withPermission(permission)
        .withListener(
            CallbacksPermissionListener(
                activity,
                grantedCallback,
                deniedCallback,
                permissionTitle,
                permissionRationale
            )
        )
        .check()
}

class CallbacksPermissionListener(
    val context: Context,
    val grantedCallback: (PermissionGrantedResponse?) -> Unit,
    val deniedCallback: (PermissionDeniedResponse?) -> Unit,
    val permissionTitle: () -> String?,
    val permissionRationale: () -> String?
) : BasePermissionListener() {

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        super.onPermissionGranted(response)
        grantedCallback(response)
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        super.onPermissionDenied(response)
        CommonAlert.Builder(context)
            .title(permissionTitle())
            .cancelable(false)
            .message(permissionRationale())
            .positiveButton(android.R.string.ok) { dialog, which ->
                dialog?.dismiss()
                deniedCallback(response)
            }
            .show()
    }
}