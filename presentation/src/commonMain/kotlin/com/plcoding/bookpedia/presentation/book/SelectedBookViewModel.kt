package com.plcoding.bookpedia.presentation.book

import androidx.lifecycle.ViewModel
import com.plcoding.bookpedia.model.book.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedBookViewModel: ViewModel() {
    private val _book = MutableStateFlow<Book?>(null)
    val book = _book.asStateFlow()

    fun onSelectBook(book: Book?) {
        _book.value = book
    }
}
