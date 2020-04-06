package com.korz.memorycards.model.dao

import androidx.room.*
import com.korz.memorycards.interfaces.Chapter
import com.korz.memorycards.model.dbKeys.ChapterKeys

@Dao
interface ChapterDao {

    @Query("SELECT * from ${ChapterKeys.TABLE_NAME} WHERE ${ChapterKeys.PARENT_CHAPTER_ID} = null")
    fun getTopChapters(): List<ChapterRoom>

    @Query("SELECT * from ${ChapterKeys.TABLE_NAME} WHERE ${ChapterKeys.PARENT_CHAPTER_ID} = :parentId")
    fun getChildChapters(parentId: Long): List<ChapterRoom>

    @Query("SELECT * from ${ChapterKeys.TABLE_NAME} WHERE id = :chapterId")
    fun getChapterById(chapterId: Long): ChapterRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chapter: Chapter)

    @Query("DELETE FROM ${ChapterKeys.TABLE_NAME}")
    suspend fun deleteAll()

}

@Entity(tableName = ChapterKeys.TABLE_NAME)
data class ChapterRoom(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = ChapterKeys.IMAGE) val image: String,
    @ColumnInfo(name = ChapterKeys.PARENT_CHAPTER_ID) val parentChapterId: Long
) {

}