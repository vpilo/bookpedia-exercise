package com.plcoding.bookpedia.presentation.di

import com.plcoding.bookpedia.presentation.book.SelectedBookViewModel
import com.plcoding.bookpedia.presentation.book.list.BookListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
}
