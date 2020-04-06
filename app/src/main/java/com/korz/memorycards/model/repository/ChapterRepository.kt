package com.korz.memorycards.model.repository

import com.korz.memorycards.interfaces.Chapter
import com.korz.memorycards.model.dao.ChapterDao
import com.korz.memorycards.model.dao.ChapterRoom
import com.korz.memorycards.model.entity.ChapterImpl


class ChapterRepository(private val chapterDao: ChapterDao) {

    suspend fun insert(chapter: Chapter) {
        chapterDao.insert(chapter)
    }

    fun getTopChapters(): List<Chapter> {
        return chapterDao
            .getTopChapters()
            .map {
                it.toChapter(chapterDao)
            }

    }

    fun ChapterRoom.toChapter(dao: ChapterDao): ChapterImpl {
        return ChapterImpl(
            id = this.id,
            image = this.image,
            parentChapter = dao.getChapterById(this.parentChapterId).toChapter(dao),
            childChapters = dao.getChildChapters(this.id).map { it.toChapter(dao) }
        )
    }


}