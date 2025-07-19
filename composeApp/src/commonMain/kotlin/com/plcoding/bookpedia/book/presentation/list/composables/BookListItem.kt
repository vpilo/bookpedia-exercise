package com.plcoding.bookpedia.book.presentation.list.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_open
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.DefaultButtonSize
import com.plcoding.bookpedia.core.presentation.DefaultPadding
import com.plcoding.bookpedia.core.presentation.LightBlueSurface
import com.plcoding.bookpedia.core.presentation.RoundedShape
import com.plcoding.bookpedia.core.presentation.composables.Rating
import com.plcoding.bookpedia.core.presentation.composables.Thumbnail
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BookListItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedShape,
        color = LightBlueSurface,
        modifier = modifier
            .clickable(onClick = onClick),
    ) {

        Row(
            modifier = Modifier
                .padding(DefaultPadding.Medium)
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Thumbnail(
                imageUrl = book.imageUrl,
                contentDescription = book.title,
                aspectRatio = .65f,
                matchHeightConstraintsFirst = true,
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = book.authors.first(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                book.averageRating?.let {
                    Rating(
                        rating = it,
                        modifier = Modifier.padding(vertical = DefaultPadding.Tiny),
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(Res.string.book_open),
                modifier = Modifier
                    .size(DefaultButtonSize)
            )
        }
    }
}


@Preview
@Composable
private fun PreviewBookListItem() {
    BookListItem(
        book = Book(
            id = "id",
            title = "Book Title",
            imageUrl = "",
            authors = listOf("Author"),
            description = "description",
            averageRating = 3.5,
        ),
        onClick = {},
    )
}
