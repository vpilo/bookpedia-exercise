package com.plcoding.bookpedia.presentation.book

import com.plcoding.bookpedia.model.book.Book
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

sealed interface PreviewParameterProviders {

    data object Books : PreviewParameterProviders {
        val none = emptyList<Book>()

        val one = listOf(
                Book(
                    id = "book",
                    title = "The One Book",
                    imageUrl = "",
                    languages = emptyList(),
                    authors = listOf("Author"),
                    description = "",
                )
            )

        val many = (1..64).map { idx ->
            Book(
                id = idx.toString(),
                title = "Book $idx",
                imageUrl = "",
                languages = emptyList(),
                authors = listOf("Author of book $idx"),
                description = "",
            )
        }
    }

    /**
     * In theory add
     *     @PreviewParameter(BookPreviewParameterProvider::class, limit = 2) books: List<Book>
     * as parameter for a Preview. But `books` is unexpectedly null.
     */
    object BooksPPPNotWorking : PreviewParameterProvider<List<Book>>, PreviewParameterProviders {
        override val values: Sequence<List<Book>> = sequenceOf(
            Books.none,
            Books.one,
            Books.many
        )
    }

}
