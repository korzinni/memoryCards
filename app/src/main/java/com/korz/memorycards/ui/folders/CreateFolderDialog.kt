package com.korz.memorycards.ui.folders

import android.os.Bundle
import com.korz.memorycards.R
import com.korz.memorycards.databinding.DialogCreateFolderBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.id_east.gm.ui.common.BaseBottomSheetDialog
import ru.id_east.gm.ui.common.setDumbClickListener
import ru.id_east.gm.utils.notNullObserve

class CreateFolderDialog : BaseBottomSheetDialog<DialogCreateFolderBinding>() {
    override val layoutId = R.layout.dialog_create_folder

    private val viewModel: CreateFolderViewModel by viewModel()

    val parentId by lazy {
        arguments?.getLong(PARENT_ID) ?: throw IllegalArgumentException("parentId must not be null")
    }

    override fun onViewCreated(binding: DialogCreateFolderBinding) {

        viewModel.initId(parentId)
        binding.viewModel = viewModel
        binding.confirmButton.setDumbClickListener {
            viewModel.createFolder()
        }

        viewModel.newFolderId.notNullObserve(viewLifecycleOwner) {
            dismiss()
        }

        viewModel.error.notNullObserve(viewLifecycleOwner, defaultErrorHandler)

    }

    companion object {
        const val PARENT_ID = "prent_id"
        fun newInstance(parentFolderId: Long = -1): CreateFolderDialog {
            val fragment = CreateFolderDialog()
            fragment.arguments = Bundle().also {
                it.putLong(PARENT_ID, parentFolderId)
            }
            return fragment
        }
    }
}
