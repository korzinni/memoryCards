package com.korz.memorycards

import androidx.room.Room
import com.korz.memorycards.interfaces.FolderRepository
import com.korz.memorycards.repository.FolderRepositoryImpl
import com.korz.memorycards.repository.room.CardsDatabase
import com.korz.memorycards.ui.chaptersList.ChaptersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CardsDatabase::class.java,
            "card_database"
        ).build()
    }
}

val chapterModule = module {
    single { (get() as CardsDatabase).folderDao() }
    single<FolderRepository> { FolderRepositoryImpl(get()) }
    viewModel {
        ChaptersListViewModel(folderRepository = get())
    }
}