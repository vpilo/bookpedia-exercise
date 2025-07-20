package com.plcoding.bookpedia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.plcoding.bookpedia.navigation.Route
import com.plcoding.bookpedia.presentation.book.SelectedBookViewModel
import com.plcoding.bookpedia.presentation.book.detail.BookDetailAction
import com.plcoding.bookpedia.presentation.book.detail.BookDetailScreenRoot
import com.plcoding.bookpedia.presentation.book.detail.BookDetailViewModel
import com.plcoding.bookpedia.presentation.book.list.BookListScreenRoot
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.RootNavGraph,
        ) {
            navigation<Route.RootNavGraph>(startDestination = Route.BookList) {
                composable<Route.BookList> { entry ->
                    val selectedBookViewModel = entry.sharedViewModel<SelectedBookViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedBookViewModel.onSelectBook(null)
                    }

                    BookListScreenRoot(
                        viewModel = koinViewModel(),
                        onBookClicked = { book ->
                            selectedBookViewModel.onSelectBook(book)
                            navController.navigate(Route.BookDetail(book.id))
                        },
                    )
                }

                composable<Route.BookDetail> { entry ->
                    val viewModel = koinViewModel<BookDetailViewModel>()
                    val selectedBookViewModel = entry.sharedViewModel<SelectedBookViewModel>(navController)
                    val selectedBook by selectedBookViewModel.book.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.let {
                            viewModel.onAction(BookDetailAction.SelectedBookChanged(it))
                        }
                    }
                    BookDetailScreenRoot(
                        onBackClicked = { navController.navigateUp() },
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) { navController.getBackStackEntry(navGraphRoute) }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}
