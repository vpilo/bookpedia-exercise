package com.plcoding.bookpedia.book.data.repository

import com.plcoding.bookpedia.book.data.ktx.toBook
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.DataSourceError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map

class BookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
) {
    suspend fun search(query: String): Result<List<Book>, DataSourceError.Remote> =
        remoteBookDataSource.search(query = query, resultLimit = null)
            .map { response ->
                response.results.map { it.toBook() }
            }
}
