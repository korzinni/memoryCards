package com.korz.memorycards.repository

import com.korz.memorycards.interfaces.Folder
import com.korz.memorycards.interfaces.FolderRepository
import com.korz.memorycards.repository.room.dao.FolderDao
import com.korz.memorycards.repository.room.entity.FolderDb
import com.korz.memorycards.repository.room.entity.FolderImpl


class FolderRepositoryImpl(private val folderDao: FolderDao) : FolderRepository {


    suspend fun FolderDb.toFolder(dao: FolderDao): FolderImpl {
        return FolderImpl(
            id = this.id,
            image = this.image,
            name = this.name,
            parentId = this.parentId,
            innerFolders = if (this.id != null) dao.getChildFolders(this.id).map { it.toFolder(dao) } else listOf()
        )
    }

    fun Folder.toDb(): FolderDb {
        return FolderDb(id, image, name, parentId)
    }

    override suspend fun getTreeFolders(): List<Folder> {
        return folderDao
            .getTopFolders()
            .map {
                it.toFolder(folderDao)
            }
    }

    override suspend fun getById(id: Long): Folder {
        return folderDao.getFolderById(id).toFolder(folderDao)
    }

    override suspend fun updateFolder(chapter: Folder) {
        folderDao.insert(chapter.toDb())
    }

    override suspend fun createNewFolder(parentId: Long, name: String): Long {
        return folderDao.insert(FolderDb(null, null, name, parentId))
    }

    override suspend fun deleteFolder(chapter: Folder) {

    }


}