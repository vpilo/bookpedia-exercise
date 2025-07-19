package com.plcoding.bookpedia

import android.app.Application
import org.koin.android.ext.koin.androidContext

class BookpediaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@BookpediaApplication)
        }
    }
}
