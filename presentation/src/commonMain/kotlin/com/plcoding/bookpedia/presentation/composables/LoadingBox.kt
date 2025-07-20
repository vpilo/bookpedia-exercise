package com.plcoding.bookpedia.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.bookpedia.presentation.PulseAnimation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoadingBox(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize(),
    ) {
        PulseAnimation()
    }
}

@Preview
@Composable
private fun PreviewLoadingBox() {
    MaterialTheme {
        LoadingBox()
    }
}
