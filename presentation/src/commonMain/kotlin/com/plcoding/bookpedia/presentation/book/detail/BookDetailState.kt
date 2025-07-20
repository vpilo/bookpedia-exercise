package com.plcoding.bookpedia.presentation.book.detail

import com.plcoding.bookpedia.model.book.Book

data class BookDetailState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val book: Book? = null,
)
