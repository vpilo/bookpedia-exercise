package com.plcoding.bookpedia.presentation.book.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.model.book.repository.BookRepository
import com.plcoding.bookpedia.model.onError
import com.plcoding.bookpedia.model.onSuccess
import com.plcoding.bookpedia.presentation.ktx.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class BookListViewModel(
    private val bookRepository: BookRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            observeSearchQuery()
            observeFavorites()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5.seconds),
            _state.value,
        )

    private var searchJob: Job? = null

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClicked -> {
                viewModelScope.launch {
                    bookRepository.save(book = action.book)
                    println("BOOK ${action.book.id} (${action.book.title}) SAVED")
                }
            }

            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTab = action.tab)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500.milliseconds)
            .onEach { query ->
                when {
                    query.isBlank() -> _state.update { it.copy(errorMessage = null, searchResults = emptyList(), isLoading = false) }
                    query.length > 2 -> startSearch(query)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeFavorites() {
        bookRepository
            .knownBooks()
            .onEach { list ->
                _state.update { state -> state.copy(favorites = list.filter { it.isFavorite }) }
            }
            .launchIn(viewModelScope)
    }

    private fun startSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            bookRepository.search(query)
                .onSuccess { searchResults ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            searchResults = searchResults,
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.toUiText(),
                            isLoading = false,
                            searchResults = emptyList(),
                        )
                    }
                }
        }
    }
}
