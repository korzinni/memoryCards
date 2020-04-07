package com.korz.memorycards.interfaces


interface Folder {
    val innerFolders: List<Folder>?
    val id: Long
    val parentId: Long?
    val image: String
}

interface FolderRepository {
    fun getTreeFolders(): List<Folder>
    suspend fun updateFolder(chapter: Folder)
    suspend fun deleteFolder(chapter: Folder)
}