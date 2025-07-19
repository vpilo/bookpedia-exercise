package com.plcoding.bookpedia.presentation.book.list

import com.plcoding.bookpedia.model.book.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String) : BookListAction
    data class OnBookClicked(val book: Book) : BookListAction
    data class OnTabSelected(val tab: BookListTab) : BookListAction
}
