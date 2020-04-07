package com.korz.memorycards.repository.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.korz.memorycards.interfaces.Folder


@Entity(tableName = FolderKeys.TABLE_NAME)
data class FolderDb(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = FolderKeys.IMAGE) val image: String?,
    @ColumnInfo(name = FolderKeys.NAME) val name: String?,
    @ColumnInfo(name = FolderKeys.PARENT_FOLDER_ID) val parentId: Long?
) {

}

data class FolderImpl(
    override val innerFolders: List<Folder>?,
    override val id: Long?,
    override val parentId: Long?,
    override val image: String?,
    override val name: String?
) : Folder {

}

object FolderKeys {
    const val TABLE_NAME = "folder"
    const val PARENT_FOLDER_ID = "parent_folder_id"
    const val IMAGE = "folder_image"
    const val NAME = "folder_name"
}



