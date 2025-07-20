package com.plcoding.bookpedia.data.book.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters

@Database(
    entities = [BookEntity::class],
    version = 1,
)
@ConstructedBy(BookDatabaseConstructor::class)
@TypeConverters(
    StringListTypeConverter::class
)
abstract class FavoriteBookDatabase: RoomDatabase() {
    abstract val favoriteBookDao: FavoriteBookDao

    companion object {
        const val DB_NAME = "books.db"
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<FavoriteBookDatabase> {
    override fun initialize(): FavoriteBookDatabase
}
