package com.korz.memorycards.ui.folders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.korz.memorycards.interfaces.Folder
import com.korz.memorycards.interfaces.FolderRepository
import ru.id_east.gm.ui.common.BaseViewModel

class FoldersViewModel(private val folderRepository: FolderRepository) : BaseViewModel() {

    private val _folders = MutableLiveData<List<FolderItem>>()
    val folders: LiveData<List<FolderItem>> = _folders

    fun requestFolders() {
        loadData {

            _folders.value = folderRepository.getTreeFolders().map { FolderItem(it, 0, false) }
        }
    }

    fun toggleFolder(folderItem: FolderItem) {
        folderItem.extend = !folderItem.extend //switch before create copy list
        val list = _folders.value?.map { FolderItem(it, it.deep, it.extend) }?.toMutableList()
        if (folderItem.extend) {
            val innerList = folderItem.innerFolders
            innerList?.let {
                list?.addAll(
                    list.indexOfFirst { it.id == folderItem.id } + 1,
                    it.map { item -> FolderItem(item, folderItem.deep + 1, false) })
            }
        } else {
            removeChildsFolder(list, folderItem)
        }
        _folders.value = list
    }

    fun removeChildsFolder(
        list: MutableList<FolderItem>?,
        folderItem: Folder
    ) {
        folderItem.innerFolders?.forEach { removeChildsFolder(list, it) }
        list?.firstOrNull { it.id == folderItem.id }?.extend = false
        list?.removeAll { it.parentId == folderItem.id }
    }

    class FolderItem(folder: Folder, val deep: Int, var extend: Boolean) : Folder by folder
}