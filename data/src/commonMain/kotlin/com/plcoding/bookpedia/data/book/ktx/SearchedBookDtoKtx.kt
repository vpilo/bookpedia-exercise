package com.plcoding.bookpedia.data.book.ktx

import com.plcoding.bookpedia.data.dto.SearchedBookDto
import com.plcoding.bookpedia.model.book.Book

fun SearchedBookDto.toBook(): Book =
    Book(
        id = id.removePrefix("/works/"),
        title = title,
        imageUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames,
        description = null,
        languages = languages,
        firstPublishYear = publishYear,
        averageRating = ratingAverage,
        ratingCount = ratingCount,
        numPages = numPagesMedian,
        numEditions = numEditions,
        isFavorite = false,
    )
