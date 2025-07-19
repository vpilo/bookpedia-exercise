package com.plcoding.bookpedia.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.bookpedia.core.presentation.UiText
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessageBox(
    text: UiText, isError: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(
            text = text.asString(),
            style = MaterialTheme.typography.titleLarge,
            color = if(isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
private fun PreviewErrorBox() {
    MaterialTheme {
        MessageBox(UiText.DynamicString("An error occurred."), isError = true)
    }
}
