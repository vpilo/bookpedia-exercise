package com.plcoding.bookpedia.presentation.book.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.presentation.generated.resources.Res
import cmp_bookpedia.presentation.generated.resources.back
import cmp_bookpedia.presentation.generated.resources.favorites_add
import cmp_bookpedia.presentation.generated.resources.favorites_remove
import com.plcoding.bookpedia.presentation.DarkBlue
import com.plcoding.bookpedia.presentation.DefaultPadding
import com.plcoding.bookpedia.presentation.RoundedShapeCornerSizeSmall
import com.plcoding.bookpedia.presentation.SandYellow
import com.plcoding.bookpedia.presentation.book.PreviewParameterProviders
import com.plcoding.bookpedia.presentation.book.detail.composables.BlurredImageBackground
import com.plcoding.bookpedia.presentation.composables.LoadingBox
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
    ) {
        if (state.isLoading || state.book == null) {
            BookDetailScreenBackButton(onAction = onAction)
            LoadingBox()
            return@Surface
        }

        Column {
            BookDetailScreenHeader(state, onAction, modifier)
            Spacer(modifier = Modifier.height(DefaultPadding.Small))

            val book = checkNotNull(state.book) { "This Composable can only be called with a valid book instance." }
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
            )
            Text(
                text = book.authors.first(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
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
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val book = checkNotNull(state.book) { "This Composable can only be called with a valid book instance." }

    BlurredImageBackground(
        imageUrl = book.imageUrl,
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
                    imageUrl = book.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(fraction = .4f)
                        .border(2.dp, color = DarkBlue, shape = borderClip)
                        .clip(borderClip)
                        .align(Alignment.TopCenter),
                )
                IconButton(
                    onClick = { onAction(BookDetailAction.FavoriteClicked) },
                    modifier = Modifier
                        .padding(DefaultPadding.Small)
                        .align(Alignment.BottomEnd)
                        .background(buttonBackgroundBrush)
                        .shadow(20.dp),
                ) {
                    Icon(
                        imageVector = if (state.isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        tint = Color.Red,
                        contentDescription = stringResource(
                            if (state.isFavorite) Res.string.favorites_remove else Res.string.favorites_add,
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
