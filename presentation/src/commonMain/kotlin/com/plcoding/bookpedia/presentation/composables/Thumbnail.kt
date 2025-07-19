package com.plcoding.bookpedia.presentation.composables

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.bookpedia.presentation.DefaultThumbnailSize

@Composable
fun Thumbnail(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    aspectRatio: Float = 1f,
    matchHeightConstraintsFirst: Boolean = false,
) {
    RemoteImage(
        imageUrl = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .size(DefaultThumbnailSize)
            .aspectRatio(
                ratio = aspectRatio,
                matchHeightConstraintsFirst = matchHeightConstraintsFirst,
            ),
    )
}
