package com.plcoding.bookpedia

import com.plcoding.bookpedia.data.di.dataModule
import com.plcoding.bookpedia.di.appPlatformModule
import com.plcoding.bookpedia.di.sharedModule
import com.plcoding.bookpedia.presentation.di.presentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

internal fun initializeKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            appPlatformModule,
            sharedModule,
            dataModule,
            presentationModule
        )
    }
}
