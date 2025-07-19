package com.plcoding.bookpedia

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.bookpedia.book.data.HttpClientFactory
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.repository.DefaultBookRepository
import com.plcoding.bookpedia.presentation.book.list.BookListScreenRoot
import com.plcoding.bookpedia.presentation.book.list.BookListViewModel
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(httpClientEngine: HttpClientEngine) {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            BookListScreenRoot(
                viewModel = remember {
                    BookListViewModel(
                        bookRepository = DefaultBookRepository(
                            remoteBookDataSource = KtorRemoteBookDataSource(
                                httpClient = HttpClientFactory.create(engine = httpClientEngine),
                            ),
                        ),
                    )
                },
                onBookClicked = {},
            )
        }
    }
}
