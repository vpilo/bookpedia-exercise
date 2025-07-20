package com.plcoding.bookpedia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.plcoding.bookpedia.navigation.Route
import com.plcoding.bookpedia.presentation.book.detail.BookDetailScreenRoot
import com.plcoding.bookpedia.presentation.book.list.BookListScreenRoot
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

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
                composable<Route.BookList> {
                    BookListScreenRoot(
                        viewModel = koinViewModel(),
                        onBookClicked = { book ->
                            navController.navigate(Route.BookDetail(book.id))
                        },
                    )
                }

                composable<Route.BookDetail> {
                    BookDetailScreenRoot(
                        onBackClicked = { navController.navigateUp() },
                        viewModel = koinViewModel(),
                    )
                }
            }
        }
    }
}
