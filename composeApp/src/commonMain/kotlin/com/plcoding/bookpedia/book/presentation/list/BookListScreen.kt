package com.plcoding.bookpedia.book.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.search_no_results
import cmp_bookpedia.composeapp.generated.resources.search_start_searching
import cmp_bookpedia.composeapp.generated.resources.search_tab_favs
import cmp_bookpedia.composeapp.generated.resources.search_tab_results
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.PreviewParameterProviders
import com.plcoding.bookpedia.book.presentation.list.composables.BookList
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DarkYellowSurface
import com.plcoding.bookpedia.core.presentation.DefaultPadding
import com.plcoding.bookpedia.core.presentation.MaxUserInterfaceWidth
import com.plcoding.bookpedia.core.presentation.RoundedShapeCornerSize
import com.plcoding.bookpedia.core.presentation.SandYellow
import com.plcoding.bookpedia.core.presentation.UiText
import com.plcoding.bookpedia.core.presentation.composables.MessageBox
import com.plcoding.bookpedia.core.presentation.composables.LoadingBox
import com.plcoding.bookpedia.core.presentation.composables.SearchBar
import org.jetbrains.compose.resources.stringResource
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
        },
        modifier = modifier,
    )
}

@Composable
private fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val tabPagerState = rememberPagerState { 2 }

    val searchResultListState = rememberLazyListState()
    val favoriteListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultListState.animateScrollToItem(0)
    }

    Column(
        modifier = modifier
            .widthIn(max = MaxUserInterfaceWidth)
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = { onAction(BookListAction.OnSearchQueryChange(it)) },
            onImeSearchAction = {
                keyboardController?.hide()
                onAction(BookListAction.OnSearchQueryChange(state.searchQuery))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(DefaultPadding.Medium)
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(
                topStart = RoundedShapeCornerSize,
                topEnd = RoundedShapeCornerSize,
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTab.index,
                    indicator = {
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier
                                .tabIndicatorOffset(it[state.selectedTab.index])
                        )
                    },
                    modifier = Modifier
                        .padding(DefaultPadding.Medium)
                ) {
                    Tab(
                        selected = state.selectedTab == BookListTab.Search,
                        onClick = { onAction(BookListAction.OnTabSelected(BookListTab.Search)) },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = DarkYellowSurface,
                    ) {
                        Text(
                            text = stringResource(Res.string.search_tab_results),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Tab(
                        selected = state.selectedTab == BookListTab.Favorites,
                        onClick = { onAction(BookListAction.OnTabSelected(BookListTab.Favorites)) },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = DarkYellowSurface,
                    ) {
                        Text(
                            text = stringResource(Res.string.search_tab_favs),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(DefaultPadding.Small)
                        )
                    }
                }
                HorizontalPager(
                    state = tabPagerState,
                ) { currentPage ->
                    val items = when (currentPage) {
                        1 -> state.favorites
                        else -> state.searchResults
                    }
                    val listState = when (currentPage) {
                        1 -> favoriteListState
                        else -> searchResultListState
                    }
                    when {
                        state.isLoading -> LoadingBox()
                        state.errorMessage != null -> MessageBox(text = state.errorMessage, true)
                        else -> {
                            val messageId =
                                if (state.searchQuery.isEmpty()) Res.string.search_start_searching else Res.string.search_no_results
                            BookList(
                                books = items,
                                emptyListMessage = UiText.StringResourceId(
                                    id = messageId,
                                    args = arrayOf(state.searchQuery)
                                ),
                                onClick = { onAction(BookListAction.OnBookClicked(it)) },
                                scrollState = listState
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBookListScreen_Many() {
    MaterialTheme {
        BookListScreen(
            state = BookListState(searchResults = PreviewParameterProviders.Books.many),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun PreviewBookListScreen_One() {
    MaterialTheme {
        BookListScreen(
            state = BookListState(searchResults = PreviewParameterProviders.Books.one),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun PreviewBookListScreen_None() {
    MaterialTheme {
        BookListScreen(
            state = BookListState(
                searchQuery = "query",
                searchResults = PreviewParameterProviders.Books.none
            ),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun PreviewBookListScreen_Loading() {
    MaterialTheme {
        BookListScreen(
            state = BookListState(searchResults = listOf(), isLoading = true),
            onAction = {},
        )
    }
}


@Preview
@Composable
private fun PreviewBookListScreen_Favorites() {
    MaterialTheme {
        BookListScreen(
            state = BookListState(
                favorites = PreviewParameterProviders.Books.many,
                selectedTab = BookListTab.Favorites
            ),
            onAction = {},
        )
    }
}
