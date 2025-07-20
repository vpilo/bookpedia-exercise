package com.plcoding.bookpedia.data.book.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.java.KoinJavaComponent.get

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        val appContext = get<Context>(Context::class.java).applicationContext
        val rootPath = appContext.getDatabasePath(FavoriteBookDatabase.DB_NAME)
        return Room.databaseBuilder(
            context = appContext,
            name = rootPath.absolutePath
        )
    }
}
