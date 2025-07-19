package com.plcoding.bookpedia.book.domain

data class Book(
    val id: String,
    val title: String,
    val imageUrl: String,
    val authors: List<String>,
    val description: String,
    val languages: List<String> = emptyList(),
    val firstPublishYear: Int? = null,
    val averageRating: Double? = null,
    val ratingCount: Int? = null,
    val numPages: Int? = null,
    val numEditions: Int? = null,
)
