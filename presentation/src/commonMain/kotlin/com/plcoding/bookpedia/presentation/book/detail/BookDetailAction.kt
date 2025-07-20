package com.plcoding.bookpedia.presentation.book.detail

sealed interface BookDetailAction {
    data object BackClicked : BookDetailAction

    data object FavoriteClicked : BookDetailAction
}
