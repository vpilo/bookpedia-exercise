package com.plcoding.bookpedia.presentation.book.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.model.book.Book
import com.plcoding.bookpedia.model.book.repository.BookRepository
import com.plcoding.bookpedia.model.onError
import com.plcoding.bookpedia.model.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val bookId: String? = savedStateHandle["id"]

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            checkNotNull(bookId) { "Detail view model doesn't know which book to show" }
            bookRepository.get(bookId)
                .onError {
                    println("BOOK $bookId NOT FOUND")
                    _state.update { BookDetailState() }
                }
                .onSuccess { foundBook ->
                    println("BOOK $bookId LOADED")
                    _state.update { it.copy(book = foundBook, isLoading = false) }
                }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5.seconds),
            _state.value,
        )

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.FavoriteClicked -> {
                viewModelScope.launch {
                    val id = bookId ?: return@launch
                    val book = _state.value.book ?: return@launch
                    bookRepository.setFavorite(id, !book.isFavorite)
                        .onSuccess { newState ->
                            _state.update {
                                println("BOOK $bookId NEW FAVORITE STATE $newState")
                                it.copy(book = book.copy(isFavorite = newState))
                            }
                        }
                }
            }

            else -> Unit
        }
    }
}
