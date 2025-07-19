package com.plcoding.bookpedia.book.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.composables.SearchBar
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DefaultPadding
import com.plcoding.bookpedia.core.presentation.MaxUserInterfaceWidth
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: BookListViewModel = koinViewModel(),
    onBookClicked: (Book) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookListAction.OnBookClicked -> onBookClicked(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        SearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = { onAction(BookListAction.OnSearchQueryChange(it)) },
            onImeSearchAction = {
                keyboardController?.hide()
                onAction(BookListAction.OnSearchQueryChange(state.searchQuery))
            },
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = MaxUserInterfaceWidth)
                .padding(DefaultPadding)
        )
    }
}

@Preview
@Composable
private fun PreviewBookListScreen() {
    MaterialTheme {
        BookListScreen(
            state = BookListState(),
            onAction = {},
        )
    }
}
