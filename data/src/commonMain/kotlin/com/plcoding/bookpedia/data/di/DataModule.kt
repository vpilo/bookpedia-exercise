package com.plcoding.bookpedia.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.plcoding.bookpedia.data.book.database.DatabaseFactory
import com.plcoding.bookpedia.data.book.database.FavoriteBookDatabase
import com.plcoding.bookpedia.data.book.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.data.book.network.RemoteBookDataSource
import com.plcoding.bookpedia.data.book.repository.DefaultBookRepository
import com.plcoding.bookpedia.model.book.repository.BookRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule: Module = module {
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()

    singleOf(::DefaultBookRepository).bind<BookRepository>()
    single { DatabaseFactory() }
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single {
        get<FavoriteBookDatabase>().favoriteBookDao
    }
}
