package com.plcoding.bookpedia.data.book.network

import com.plcoding.bookpedia.data.dto.BookDetailDto
import com.plcoding.bookpedia.data.dto.SearchResponseDto
import com.plcoding.bookpedia.data.dto.SearchedBookDto
import com.plcoding.bookpedia.data.network.ktx.safeCall
import com.plcoding.bookpedia.model.DataSourceError
import com.plcoding.bookpedia.model.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.elementNames

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {

    override suspend fun search(query: String, resultLimit: Int?): Result<SearchResponseDto, DataSourceError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(urlString = "$BASE_SERVICE_URL/search.json") {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                @OptIn(ExperimentalSerializationApi::class)
                parameter(
                    "fields",
                    SearchedBookDto.serializer().descriptor.elementNames.joinToString(",")
                )
            }
        }
    }
    override suspend fun getDescription(id: String): Result<BookDetailDto, DataSourceError.Remote> {
        return safeCall<BookDetailDto> {
            httpClient.get(urlString = "$BASE_SERVICE_URL/works/$id.json") {
                @OptIn(ExperimentalSerializationApi::class)
                parameter(
                    "fields",
                    BookDetailDto.serializer().descriptor.elementNames.joinToString(",")
                )
            }
        }
    }

    companion object {
        const val BASE_SERVICE_URL = "https://openlibrary.org"
    }
}
