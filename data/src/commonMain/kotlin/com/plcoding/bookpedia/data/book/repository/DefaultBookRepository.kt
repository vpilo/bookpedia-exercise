package com.plcoding.bookpedia.data.book.repository

import com.plcoding.bookpedia.data.book.ktx.toBook
import com.plcoding.bookpedia.data.book.network.RemoteBookDataSource
import com.plcoding.bookpedia.model.DataSourceError
import com.plcoding.bookpedia.model.Result
import com.plcoding.bookpedia.model.book.Book
import com.plcoding.bookpedia.model.book.repository.BookRepository
import com.plcoding.bookpedia.model.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
): BookRepository {
    override suspend fun search(query: String): Result<List<Book>, DataSourceError.Remote> =
        remoteBookDataSource.search(query = query, resultLimit = null)
            .map { response ->
                response.results.map { it.toBook() }
            }

    override suspend fun getDescription(id: String): Result<String?, DataSourceError> =
        remoteBookDataSource.getDescription(id)
            .map { response ->
                response.description
            }
}
