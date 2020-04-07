package com.korz.memorycards.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.korz.memorycards.repository.room.dao.FolderDao
import com.korz.memorycards.repository.room.entity.FolderDb
import com.korz.memorycards.repository.room.entity.FolderImpl

@Database(entities = arrayOf(FolderDb::class), version = 1, exportSchema = false)
abstract class CardsDatabase : RoomDatabase() {

    abstract fun folderDao(): FolderDao
}