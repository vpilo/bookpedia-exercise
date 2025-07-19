package com.plcoding.bookpedia.presentation.book.detail

import com.plcoding.bookpedia.model.book.Book

sealed interface BookDetailAction {
    data object BackClicked : BookDetailAction

    data object FavoriteClicked  : BookDetailAction

    data class SelectedBookChanged(val book: Book) : BookDetailAction
}
