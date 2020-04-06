package com.korz.memorycards.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.korz.memorycards.model.dao.ChapterDao
import com.korz.memorycards.model.entity.ChapterImpl

@Database(entities = arrayOf(ChapterImpl::class), version = 1, exportSchema = false)
abstract class CardsDatabase : RoomDatabase() {

    abstract fun chapterDao(): ChapterDao

    companion object {
        @Volatile
        private var INSTANCE: CardsDatabase? = null

        fun getDatabase(context: Context): CardsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardsDatabase::class.java,
                    "card_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}