package com.korz.memorycards.interfaces


interface Folder {
    val innerFolders: List<Folder>?
    val id: Long?
    val parentId: Long?
    val image: String?
    val name: String?
}

interface FolderRepository {
    suspend fun getTreeFolders(): List<Folder>
    suspend fun getById(id: Long): Folder
    suspend fun createNewFolder(parentId: Long, name: String): Long
    suspend fun updateFolder(chapter: Folder)
    suspend fun deleteFolder(chapter: Folder)
}