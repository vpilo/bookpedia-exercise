package com.plcoding.bookpedia.book.domain

import com.plcoding.bookpedia.core.domain.Error

sealed interface DataSourceError: Error {

    enum class Remote : DataSourceError {
        NoInternetConnection,
        TimeOut,
        TooManyRequests,
        Server,
        Serialization,
        Unknown,
    }

    enum class Local : DataSourceError {
        DiskFull,
    }
}
