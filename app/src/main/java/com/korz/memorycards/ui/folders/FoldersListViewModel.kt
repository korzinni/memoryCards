package com.korz.memorycards.ui.folders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.korz.memorycards.interfaces.Folder
import com.korz.memorycards.interfaces.FolderRepository
import com.korz.memorycards.repository.room.entity.FolderImpl
import ru.id_east.gm.ui.common.BaseViewModel

class FoldersListViewModel(private val folderRepository: FolderRepository) : BaseViewModel() {

    private val _currentFolder = MutableLiveData<Folder>()
    val currentFolder: LiveData<Folder> = _currentFolder

    private val _emptyList = MediatorLiveData<Boolean>()
    val emptyList: LiveData<Boolean> = _emptyList

    init {
        _emptyList.addSource(currentFolder) {
            _emptyList.value = it.innerFolders.isNullOrEmpty()
        }
    }

    fun requestFolder(id: Long) {
        loadData {
            if (id == -1L) {
                _currentFolder.value = FolderImpl(
                    innerFolders = folderRepository.getTreeFolders(),
                    id = -1,
                    parentId = null,
                    name = null,
                    image = null
                )
            } else {
                _currentFolder.value = folderRepository.getById(id)
            }
        }


    }

}