package com.korz.memorycards.model.entity

import com.korz.memorycards.interfaces.Chapter
import com.korz.memorycards.model.dao.ChapterRoom


data class ChapterImpl(
    override val parentChapter: ChapterImpl? = null,
    override val childChapters: List<ChapterImpl>? = null,
    override val id: Long,
    override val image: String
) : Chapter


fun ChapterImpl.extend(parentChapter: ChapterImpl?, child: List<ChapterImpl>?): ChapterImpl {
    return ChapterImpl(parentChapter, child, id, image)
}


