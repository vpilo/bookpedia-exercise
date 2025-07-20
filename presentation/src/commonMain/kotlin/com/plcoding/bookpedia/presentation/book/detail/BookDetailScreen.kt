package com.plcoding.bookpedia.presentation.book.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.presentation.generated.resources.Res
import cmp_bookpedia.presentation.generated.resources.back
import cmp_bookpedia.presentation.generated.resources.book_no_synopsis
import cmp_bookpedia.presentation.generated.resources.book_synopsis
import cmp_bookpedia.presentation.generated.resources.favorites_add
import cmp_bookpedia.presentation.generated.resources.favorites_remove
import cmp_bookpedia.presentation.generated.resources.languages
import cmp_bookpedia.presentation.generated.resources.num_pages
import cmp_bookpedia.presentation.generated.resources.rating
import com.plcoding.bookpedia.presentation.DarkBlue
import com.plcoding.bookpedia.presentation.DefaultPadding
import com.plcoding.bookpedia.presentation.RoundedShapeCornerSizeSmall
import com.plcoding.bookpedia.presentation.SandYellow
import com.plcoding.bookpedia.presentation.book.PreviewParameterProviders
import com.plcoding.bookpedia.presentation.book.detail.composables.BlurredImageBackground
import com.plcoding.bookpedia.presentation.book.detail.composables.BookChip
import com.plcoding.bookpedia.presentation.book.detail.composables.BookChipSize
import com.plcoding.bookpedia.presentation.book.detail.composables.TitledContent
import com.plcoding.bookpedia.presentation.composables.LoadingBox
import com.plcoding.bookpedia.presentation.composables.Rating
import com.plcoding.bookpedia.presentation.composables.RemoteImage
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val buttonBackgroundBrush = Brush.radialGradient(listOf(SandYellow, Color.Transparent), radius = 50f)

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel, modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        modifier = modifier,
        onAction = { action ->
            when (action) {
                is BookDetailAction.BackClicked -> onBackClicked()
                else -> viewModel.onAction(action)
            }
        },
    )
}

@Composable
private fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        val isLoading = remember(state) { state.isLoading || state.book == null }
        if (isLoading) {
            BookDetailScreenBackButton(onAction = onAction)
            LoadingBox()
            return@Surface
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val book = checkNotNull(state.book) { "This Composable can only be called with a valid book instance." }

            BookDetailScreenHeader(book.imageUrl, book.isFavorite, onAction, modifier)

            Spacer(modifier = Modifier.height(DefaultPadding.Small))

            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 2,
            )
            Text(
                text = book.authors.first(),
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.height(DefaultPadding.Small))

            Row(
                horizontalArrangement = Arrangement.spacedBy(DefaultPadding.Medium),
            ) {
                book.averageRating?.let {
                    TitledContent(title = stringResource(Res.string.rating)) {
                        BookChip {
                            book.averageRating?.let {
                                Rating(
                                    rating = it,
                                    modifier = Modifier.padding(vertical = DefaultPadding.Tiny),
                                )
                            }
                        }
                    }
                    TitledContent(title = stringResource(Res.string.num_pages)) {
                        BookChip {
                            book.numPages?.let {
                                Text(
                                    text = it.toString(),
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                        }
                    }
                }
            }
            if (book.languages.isNotEmpty()) {
                TitledContent(title = stringResource(Res.string.languages)) {
                    @OptIn(ExperimentalLayoutApi::class)
                    FlowRow(horizontalArrangement = Arrangement.Center, modifier = Modifier.wrapContentSize(Alignment.Center)) {
                        book.languages.forEach { lang ->
                            BookChip(size = BookChipSize.Small, modifier = Modifier.padding(2.dp)) {
                                Text(text = lang)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(DefaultPadding.Medium))

            Text(
                text = stringResource(Res.string.book_synopsis),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
            Text(
                text = (book.description ?: "").ifEmpty { stringResource(Res.string.book_no_synopsis) },
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = DefaultPadding.Medium),
            )
        }
    }
}

@Composable
private fun BookDetailScreenBackButton(
    onAction: (BookDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
    ) {
        IconButton(
            onClick = { onAction(BookDetailAction.BackClicked) },
            modifier = Modifier
                .padding(DefaultPadding.Small)
                .background(buttonBackgroundBrush),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(Res.string.back),
            )
        }
    }
    Spacer(modifier = Modifier.height(DefaultPadding.Medium))
}

@Composable
private fun BookDetailScreenHeader(
    imageUrl: String,
    isFavorite: Boolean,
    onAction: (BookDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    BlurredImageBackground(
        imageUrl = imageUrl,
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = DefaultPadding.Large),
        ) {
            BookDetailScreenBackButton(onAction = onAction)
            Box {
                val borderClip = RoundedCornerShape(RoundedShapeCornerSizeSmall)
                RemoteImage(
                    imageUrl = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(fraction = .4f)
                        .border(2.dp, color = DarkBlue, shape = borderClip)
                        .clip(borderClip)
                        .align(Alignment.TopCenter),
                )
                println("Current state of favorite: $isFavorite")
                IconButton(
                    onClick = { onAction(BookDetailAction.FavoriteClicked) },
                    modifier = Modifier
                        .padding(DefaultPadding.Small)
                        .align(Alignment.BottomEnd)
                        .background(buttonBackgroundBrush)
                        .shadow(20.dp),
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        tint = Color.Red,
                        contentDescription = stringResource(
                            if (isFavorite) Res.string.favorites_remove else Res.string.favorites_add,
                        ),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBookDetailScreen() {
    MaterialTheme {
        BookDetailScreen(
            state = BookDetailState(
                book = PreviewParameterProviders.Books.oneWithImage.single(),
                isLoading = false,
            ),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun PreviewBookDetailScreenLoading() {
    MaterialTheme {
        BookDetailScreen(
            state = BookDetailState(
                book = PreviewParameterProviders.Books.oneWithImage.single(),
                isLoading = true,
            ),
            onAction = {},
        )
    }
}
