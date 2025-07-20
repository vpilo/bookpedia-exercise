package com.plcoding.bookpedia.data.dto

import kotlinx.serialization.Serializable

@Serializable(with = BookDetailDtoSerializer::class)
class BookDetailDto(
    val description: String? = null
)
