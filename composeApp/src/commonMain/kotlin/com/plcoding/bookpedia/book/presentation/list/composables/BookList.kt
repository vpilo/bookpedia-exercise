package com.plcoding.bookpedia.book.presentation.list.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.DefaultPadding
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BookList(
    books: List<Book>,
    onClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(DefaultPadding.Small),
        state = scrollState,
        modifier = modifier,
    ) {
        items(
            items = books,
            key = { it.id }
        ) { book ->
            BookListItem(
                book = book,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DefaultPadding.Medium),
                onClick = { onClick(book) },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBookList() {
    MaterialTheme {
        BookList(
            books = (1..64).map { idx ->
                Book(
                    id = idx.toString(),
                    title = "Book $idx",
                    imageUrl = "",
                    languages = emptyList(),
                    authors = listOf("Author of book $idx"),
                    description = "",
                )
            },
            onClick = {}
        )
    }

}
