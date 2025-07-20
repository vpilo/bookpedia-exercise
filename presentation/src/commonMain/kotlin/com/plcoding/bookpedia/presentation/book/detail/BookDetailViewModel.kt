package com.plcoding.bookpedia.presentation.book.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            fetchDescription()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5.seconds),
            _state.value,
        )

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.FavoriteClicked -> {
            }

            is BookDetailAction.SelectedBookChanged -> {
                _state.update { it.copy(book = action.book) }
            }

            else -> Unit
        }
    }

    private fun fetchDescription() {
        bookId ?: return

        viewModelScope.launch {
            val book = state.value.book ?: return@launch
            _state.update { it.copy(isLoading = true) }

            bookRepository.getDescription(bookId)
                .onError {
                    val newBook = book.copy(description = null)
                    _state.update { it.copy(isLoading = false, book = newBook) }
                }
                .onSuccess { description ->
                    _state.update {
                        val newBook = book.copy(description = description)
                        it.copy(isLoading = false, book = newBook)
                    }
                }
        }
    }
}
