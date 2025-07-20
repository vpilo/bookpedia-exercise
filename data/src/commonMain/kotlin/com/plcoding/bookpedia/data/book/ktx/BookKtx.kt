package com.plcoding.bookpedia.data.book.ktx

import com.plcoding.bookpedia.data.book.database.BookEntity
import com.plcoding.bookpedia.model.book.Book

fun Book.toBookEntity(): BookEntity =
    BookEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        averageRating = averageRating,
        ratingCount = ratingCount,
        numPages = numPages,
        numEditions = numEditions,
        isFavorite = isFavorite,
    )

fun BookEntity.toBook(): Book =
    Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        averageRating = averageRating,
        ratingCount = ratingCount,
        numPages = numPages,
        numEditions = numEditions,
        isFavorite = isFavorite,
    )
