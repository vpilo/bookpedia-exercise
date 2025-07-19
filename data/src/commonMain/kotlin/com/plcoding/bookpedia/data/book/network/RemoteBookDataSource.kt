package com.plcoding.bookpedia.data.book.network

import com.plcoding.bookpedia.data.dto.SearchResponseDto
import com.plcoding.bookpedia.model.DataSourceError
import com.plcoding.bookpedia.model.Result

interface RemoteBookDataSource {
    suspend fun search(query: String, resultLimit: Int? = null): Result<SearchResponseDto, DataSourceError.Remote>
}
