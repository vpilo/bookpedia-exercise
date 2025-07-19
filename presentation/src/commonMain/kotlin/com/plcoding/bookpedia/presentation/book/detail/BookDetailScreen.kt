package com.plcoding.bookpedia.presentation.book.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel, modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        modifier = modifier
    )
}

@Composable
private fun BookDetailScreen(
    state: BookDetailState,
    modifier: Modifier = Modifier) {
    
}
