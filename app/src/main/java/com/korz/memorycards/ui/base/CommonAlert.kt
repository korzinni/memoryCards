package ru.id_east.gm.ui.common

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.korz.memorycards.R
import com.korz.memorycards.databinding.CommonAlertDialogBinding


class CommonAlert() : BaseDialog<CommonAlertDialogBinding>() {
    override val layoutId = R.layout.common_alert_dialog

    override fun onViewCreated(binding: CommonAlertDialogBinding) {
        binding.dialog = dialog
        binding.message = builder.message
        binding.title = builder.title
        binding.positiveButtonText = builder.positiveButtonText
        binding.positiveCallback = builder.positiveButtonListener
        binding.negativeButtonText = builder.negativeButtonText
        binding.negativeCallback = builder.negativeButtonListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(builder.cancelable)
        return dialog

    }

    lateinit var builder: Builder

    private constructor(builder: Builder) : this() {
        this.builder = builder
    }

    class Builder(val context: Context) {
        var message: String? = null
        var title: String? = null
        var positiveButtonText: String? = null
        var positiveButtonListener: DialogInterface.OnClickListener? = null
        var negativeButtonText: String? = null
        var negativeButtonListener: DialogInterface.OnClickListener? = null
        var cancelable: Boolean = true

        fun message(message: String?) = apply { this.message = message }
        fun title(title: String?) = apply { this.title = title }
        fun cancelable(cancelable: Boolean) = apply { this.cancelable = cancelable }

        fun positiveButton(
            name: String?,
            listener: ((dialog: DialogInterface?, which: Int) -> Unit)
        ) = apply {
            this.positiveButtonText = name
            this.positiveButtonListener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    listener(dialog, which)
                }
            }
        }

        fun positiveButton(
            nameId: Int,
            listener: ((dialog: DialogInterface?, which: Int) -> Unit)
        ) = apply {
            this.positiveButtonText = context.getString(nameId)
            this.positiveButtonListener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    listener(dialog, which)
                }
            }
        }

        fun negativeButton(
            name: String?,
            listener: ((dialog: DialogInterface?, which: Int) -> Unit)
        ) = apply {
            this.negativeButtonText = name
            this.negativeButtonListener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    listener(dialog, which)
                }
            }
        }

        fun negativeButton(
            nameId: Int,
            listener: ((dialog: DialogInterface?, which: Int) -> Unit)
        ) = apply {
            this.negativeButtonText = context.getString(nameId)
            this.negativeButtonListener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    listener(dialog, which)
                }
            }
        }

        fun build() = CommonAlert(this)

        fun show() {
            build().show((context as AppCompatActivity).supportFragmentManager, null)
        }
    }

}