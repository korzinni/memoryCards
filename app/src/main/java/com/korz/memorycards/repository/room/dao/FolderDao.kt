package com.korz.memorycards.repository.room.dao

import androidx.room.*
import com.korz.memorycards.repository.room.entity.FolderDb
import com.korz.memorycards.repository.room.entity.FolderImpl
import com.korz.memorycards.repository.room.entity.FolderKeys

@Dao
interface FolderDao {

    @Query("SELECT * from ${FolderKeys.TABLE_NAME} WHERE ${FolderKeys.PARENT_FOLDER_ID} = null")
    fun getTopFolders(): List<FolderDb>

    @Query("SELECT * from ${FolderKeys.TABLE_NAME} WHERE ${FolderKeys.PARENT_FOLDER_ID} = :parentId")
    fun getChildFolders(parentId: Long): List<FolderDb>

    @Query("SELECT * from ${FolderKeys.TABLE_NAME} WHERE id = :chapterId")
    fun getFolderById(chapterId: Long): FolderDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chapter: FolderDb): Long

    @Query("DELETE FROM ${FolderKeys.TABLE_NAME}")
    suspend fun deleteAll()

}
