package com.plcoding.bookpedia.data.book.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bookEntity: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getAll(): Flow<List<BookEntity>>

    @Query("SELECT * FROM BookEntity WHERE id = :id")
    suspend fun get(id: String): BookEntity?

    @Query("UPDATE BookEntity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: String, isFavorite: Boolean)

    @Query("UPDATE BookEntity SET description = :description WHERE id = :id")
    suspend fun setDescription(id: String, description: String)
}
