package com.plcoding.bookpedia.presentation.book.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.seconds

class BookDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
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
}
