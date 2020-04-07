package com.korz.memorycards.repository

import com.korz.memorycards.interfaces.Folder
import com.korz.memorycards.interfaces.FolderRepository
import com.korz.memorycards.repository.room.dao.FolderDao
import com.korz.memorycards.repository.room.entity.FolderDb
import com.korz.memorycards.repository.room.entity.FolderImpl


class FolderRepositoryImpl(private val chapterDao: FolderDao) : FolderRepository {


    fun FolderDb.toFolder(dao: FolderDao): FolderImpl {
        return FolderImpl(
            id = this.id,
            image = this.image,
            parentId = this.parentId,
            innerFolders = dao.getChildFolders(this.id).map { it.toFolder(dao) }
        )
    }

    fun Folder.toDb(): FolderDb {
        return FolderDb(id, image, parentId)
    }

    override fun getTreeFolders(): List<Folder> {
        return chapterDao
            .getTopFolders()
            .map {
                it.toFolder(chapterDao)
            }
    }

    override suspend fun updateFolder(chapter: Folder) {
        chapterDao.insert(chapter.toDb())
    }


    override suspend fun deleteFolder(chapter: Folder) {

    }


}