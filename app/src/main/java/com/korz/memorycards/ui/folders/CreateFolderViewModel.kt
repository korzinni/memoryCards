package com.korz.memorycards.ui.folders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.korz.memorycards.interfaces.FolderRepository
import ru.id_east.gm.ui.common.BaseViewModel

class CreateFolderViewModel(private val folderRepository: FolderRepository) : BaseViewModel() {

    fun createFolder() {
        loadData {
            _newFolderId.value =
                folderRepository.createNewFolder(parentId = parentId, name = name.value!!)
        }
    }

    val error: LiveData<Throwable> = _loadError
    val name = MutableLiveData<String>()
    var parentId: Long = -1
    private val _newFolderId = MutableLiveData<Long>()
    val newFolderId: LiveData<Long> = _newFolderId

    fun initId(parentId: Long) {
        this.parentId = parentId
    }

}