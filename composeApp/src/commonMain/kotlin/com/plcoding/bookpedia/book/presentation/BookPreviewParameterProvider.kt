package com.plcoding.bookpedia.book.presentation

import com.plcoding.bookpedia.book.domain.Book
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

class BookPreviewParameterProvider : PreviewParameterProvider<List<Book>> {
    override val count: Int = 1
    override val values: Sequence<List<Book>> = sequenceOf(
        emptyList(),
        listOf(
            Book(
                id = "book",
                title = "The One Book",
                imageUrl = "",
                languages = emptyList(),
                authors = listOf("Author"),
                description = "",
            )
        ),
        (1..32).map { idx ->
            Book(
                id = idx.toString(),
                title = "Book $idx",
                imageUrl = "",
                languages = emptyList(),
                authors = listOf("Author of book $idx"),
                description = "",
            )
        }
    )
}
