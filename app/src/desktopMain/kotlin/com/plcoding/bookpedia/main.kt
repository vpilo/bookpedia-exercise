package com.plcoding.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    initializeKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Bookpedia",
        ) {
            App()
        }
    }
}
