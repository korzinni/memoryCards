package com.korz.memorycards.ui.folders

import android.os.Bundle
import android.util.Log
import com.korz.memorycards.R
import com.korz.memorycards.databinding.FragmentFoldersListBinding
import com.korz.memorycards.interfaces.Folder
import com.korz.memorycards.widgets.recyclerView.SimpleRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.id_east.gm.ui.common.BaseFragment
import ru.id_east.gm.ui.common.setDumbClickListener
import ru.id_east.gm.utils.notNullObserve

class FolderListFragment : BaseFragment<FragmentFoldersListBinding>() {
    override val layoutId = R.layout.fragment_folders_list
    val parentId by lazy {
        arguments?.getLong(PARENT_ID) ?: throw IllegalArgumentException("parentId must not be null")
    }


    private val viewModel: FoldersListViewModel by viewModel()

    private val adapter =
        SimpleRecyclerAdapter<Folder>(R.layout.item_child_folder, l = { view, item, _ ->
            if (view.id == R.id.showChildFolders) {
                openFolder(item.id ?: -1)
            }
        })

    override fun onViewCreated(binding: FragmentFoldersListBinding, savedInstanceState: Bundle?) {
        binding.childFoldersList.adapter = adapter

        viewModel.currentFolder.notNullObserve(viewLifecycleOwner) {
            adapter.setDifferenceItems(it.innerFolders ?: listOf())
            binding.parentFolderTitle.text = it.name
        }

        binding.addFolderButton.setDumbClickListener {
            CreateFolderDialog.newInstance(parentId).show(parentFragmentManager, null)
        }

        binding.parentFolderBackButton.setDumbClickListener { onBackPressed() }
        viewModel.requestFolder(parentId)

    }

    fun openFolder(id: Long) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityFoldersContainer, newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val PARENT_ID = "prent_id"
        fun newInstance(parentFolderId: Long = -1): FolderListFragment {
            val fragment = FolderListFragment()
            fragment.arguments = Bundle().also {
                it.putLong(PARENT_ID, parentFolderId)
            }
            return fragment
        }
    }

}
