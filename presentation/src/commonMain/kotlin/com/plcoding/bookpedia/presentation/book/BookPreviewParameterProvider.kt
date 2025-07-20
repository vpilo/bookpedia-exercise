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
            ),
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

        val oneWithImage = listOf(
            Book(
                id = "one_book_1111",
                title = "The One Book",
                imageUrl = "file:///home/vale/downloads/holly.jpg",
                languages = listOf("ITA", "ENG", "NED"),
                authors = listOf("Author"),
                description =
                    "Lorem ipsum dolor sit amet consectetur adipiscing elit. Quisque faucibus ex sapien vitae pellentesque sem placerat. " +
                            "In id cursus mi pretium tellus duis convallis. Tempus leo eu aenean sed diam urna tempor. Pulvinar vivamus " +
                            "fringilla lacus nec metus bibendum egestas. Iaculis massa nisl malesuada lacinia integer nunc posuere. Ut " +
                            "hendrerit semper vel class aptent taciti sociosqu. Ad litora torquent per conubia nostra inceptos himenaeos." +
                            "\n" +
                            "Lorem ipsum dolor sit amet consectetur adipiscing elit. Quisque faucibus ex sapien vitae pellentesque sem " +
                            "placerat. In id cursus mi pretium tellus duis convallis. Tempus leo eu aenean sed diam urna tempor. " +
                            "Pulvinar vivamus fringilla lacus nec metus bibendum egestas. Iaculis massa nisl malesuada lacinia integer " +
                            "nunc posuere. Ut hendrerit semper vel class aptent taciti sociosqu. Ad litora torquent per conubia nostra " +
                            "inceptos himenaeos.",
                averageRating = 3.8,
                ratingCount = 276,
                numPages = 750,
                firstPublishYear = 2020,
                numEditions = 3,
            ),
        )
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
            Books.many,
        )
    }

}
