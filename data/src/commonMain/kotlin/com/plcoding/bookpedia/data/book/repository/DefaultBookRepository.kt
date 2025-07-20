package com.plcoding.bookpedia.data.book.repository

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.data.book.database.FavoriteBookDao
import com.plcoding.bookpedia.data.book.ktx.toBook
import com.plcoding.bookpedia.data.book.ktx.toBookEntity
import com.plcoding.bookpedia.data.book.network.RemoteBookDataSource
import com.plcoding.bookpedia.model.DataSourceError
import com.plcoding.bookpedia.model.Result
import com.plcoding.bookpedia.model.book.Book
import com.plcoding.bookpedia.model.book.repository.BookRepository
import com.plcoding.bookpedia.model.map
import com.plcoding.bookpedia.model.onError
import com.plcoding.bookpedia.model.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao,
) : BookRepository {
    override suspend fun search(query: String): Result<List<Book>, DataSourceError.Remote> =
        remoteBookDataSource.search(query = query, resultLimit = null)
            .map { response ->
                response.results.map { it.toBook() }
            }

    override suspend fun get(id: String): Result<Book, DataSourceError> {
        val newBook = favoriteBookDao.get(id)
            ?.let {
                val book = it.toBook()
                if (book.description == null) {
                    getDescription(id)
                        .onSuccess { desc ->
                            println("DESCRIPTION ADDED")
                            val newDescription = desc ?: ""
                            favoriteBookDao.setDescription(id, newDescription)
                            return@let book.copy(description = newDescription)
                        }
                        .onError {
                            println("FAILED TO ADD DESC")
                            return@let book }
                    null
                } else {
                    println("DESCRIPTION WAS THERE")
                    book
                }
            }
        if (newBook == null) {
            println("BOOK $id NOT FOUND!!")
            return Result.Error(DataSourceError.Local.Unknown)
        }

        return Result.Success(newBook)
    }

    override suspend fun save(book: Book): Result<Unit, DataSourceError> =
        try {
            favoriteBookDao.insert(book.toBookEntity())
            Result.Success(Unit)
        } catch (ex: SQLiteException) {
            Result.Error(DataSourceError.Local.DiskFull)
        }

    private suspend fun getDescription(id: String): Result<String?, DataSourceError> =
        remoteBookDataSource.getDescription(id)
            .map { response ->
                response.description
            }

    override fun knownBooks(): Flow<List<Book>> =
        favoriteBookDao.getAll()
            .map { entities ->
                entities.map { it.toBook() }
            }

    override suspend fun setFavorite(id: String, isFavorite: Boolean): Result<Boolean, DataSourceError.Local> =
        try {
            favoriteBookDao.setFavorite(id, isFavorite)
            println("setFavorite: $isFavorite")
            Result.Success(isFavorite)
        } catch (ex: SQLiteException) {
            Result.Error(DataSourceError.Local.DiskFull)
        }
}
