package com.plcoding.bookpedia.model.book.repository

import com.plcoding.bookpedia.model.DataSourceError
import com.plcoding.bookpedia.model.Result
import com.plcoding.bookpedia.model.book.Book

interface BookRepository {
    suspend fun search(query: String): Result<List<Book>, DataSourceError.Remote>
    suspend fun getDescription(id: String): Result<String?, DataSourceError>
}
