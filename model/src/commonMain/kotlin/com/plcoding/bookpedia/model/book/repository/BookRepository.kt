package com.plcoding.bookpedia.model.book.repository

import com.plcoding.bookpedia.model.DataSourceError
import com.plcoding.bookpedia.model.Result
import com.plcoding.bookpedia.model.book.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun search(query: String): Result<List<Book>, DataSourceError.Remote>
    suspend fun get(id: String): Result<Book, DataSourceError>

    suspend fun save(book: Book): Result<Unit, DataSourceError>

    fun knownBooks(): Flow<List<Book>>

    suspend fun setFavorite(id: String, isFavorite: Boolean): Result<Boolean, DataSourceError.Local>
}
