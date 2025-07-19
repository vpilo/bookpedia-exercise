package com.plcoding.bookpedia.core.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.ic_broken_image
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource

@Composable
fun RemoteImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
    ) {
        var imageLoadResult: Result<Painter>? by remember { mutableStateOf(null) }
        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            onSuccess = {
                imageLoadResult =
                    if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                        Result.success(it.painter)
                    } else {
                        Result.failure(Exception("image has not loaded properly"))
                    }
            },
            onError = {
                println(it.result.throwable.stackTraceToString())
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )
        when (val result = imageLoadResult) {
            null -> CircularProgressIndicator()
            else -> Image(
                painter = if (result.isSuccess) painter else painterResource(Res.drawable.ic_broken_image),
                contentDescription = contentDescription,
                contentScale = ContentScale.Fit,
            )
        }
    }
}
