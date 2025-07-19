package com.plcoding.bookpedia.presentation.book.list

import com.plcoding.bookpedia.model.book.Book
import com.plcoding.bookpedia.presentation.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val favorites: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTab: BookListTab = BookListTab.Search,
    val errorMessage: UiText? = null,
)

enum class BookListTab(val index: Int) {
    Search(0),
    Favorites(1),
}
