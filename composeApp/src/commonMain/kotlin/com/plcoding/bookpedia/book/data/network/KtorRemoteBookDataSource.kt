package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.data.network.ktx.safeCall
import com.plcoding.bookpedia.book.domain.DataSourceError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.elementNames

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {
    override suspend fun search(query: String, resultLimit: Int?): Result<SearchResponseDto, DataSourceError> {
        return safeCall {
            httpClient.get(urlString = "$BASE_SERVICE_URL/search.json") {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                @OptIn(ExperimentalSerializationApi::class)
                parameter("fields", SearchedBookDto.serializer().descriptor.elementNames.joinToString(","))
            }
        }
    }

    companion object {
        const val BASE_SERVICE_URL = "https://openlibrary.org"
    }
}
