package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import com.plcoding.bookpedia.book.domain.DataSourceError
import com.plcoding.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun search(query: String, resultLimit: Int? = null): Result<SearchResponseDto, DataSourceError.Remote>
}
