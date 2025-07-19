package com.plcoding.bookpedia

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.bookpedia.presentation.book.list.BookListScreenRoot
import com.plcoding.bookpedia.presentation.book.list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            BookListScreenRoot(
                viewModel = remember { BookListViewModel() },
                onBookClicked = {}
            )
        }
    }
}
