package com.korz.memorycards.ui.chaptersList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.korz.memorycards.interfaces.Folder
import com.korz.memorycards.interfaces.FolderRepository
import ru.id_east.gm.ui.common.BaseViewModel

class ChaptersListViewModel(private val folderRepository: FolderRepository) : BaseViewModel() {

    private val _tree = MutableLiveData<List<Folder>>()
    val tree: LiveData<List<Folder>> = _tree

    private val _emptyList = MediatorLiveData<Boolean>()
    val emptyList: LiveData<Boolean> = _emptyList

    init {
        _emptyList.addSource(tree) {
            _emptyList.value = it.isNullOrEmpty()
        }

        loadData {
            _tree.value = folderRepository.getTreeFolders()
        }
    }

    fun insertTestChapters(chapter: Folder) {

    }
}