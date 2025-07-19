package com.plcoding.bookpedia.di

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val appPlatformModule: Module = module {
    single { OkHttp.create() }
}
