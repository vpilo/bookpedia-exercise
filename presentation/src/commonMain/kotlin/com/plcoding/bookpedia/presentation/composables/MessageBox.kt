package com.plcoding.bookpedia.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.plcoding.bookpedia.presentation.UiText
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
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
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


@Preview
@Composable
private fun PreviewMessageBox() {
    MaterialTheme {
        MessageBox(UiText.DynamicString("It's okay."), isError = false)
    }
}
