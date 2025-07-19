package com.plcoding.bookpedia.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import cmp_bookpedia.presentation.generated.resources.Res
import cmp_bookpedia.presentation.generated.resources.ic_broken_image
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource

@Composable
fun RemoteImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
) {
    var imageLoadResult: Result<Painter>? by remember { mutableStateOf(null) }
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult =
                if (size.width > 1 && size.height > 1) {
                    Result.success(it.painter)
                } else {
                    Result.failure(Exception("image has not loaded properly"))
                }
        },
        onError = {
            println(it.result.throwable.stackTraceToString())
            imageLoadResult = Result.failure(it.result.throwable)
        },
    )
    val result = imageLoadResult
    when {
        result == null -> CircularProgressIndicator()
        result.isFailure -> Icon(
            painter = painterResource(Res.drawable.ic_broken_image),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.error.copy(alpha = .5f),
        )

        else -> Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            alignment = contentAlignment,
            modifier = modifier,
        )
    }
}
