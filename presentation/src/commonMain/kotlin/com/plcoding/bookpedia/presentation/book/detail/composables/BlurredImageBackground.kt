package com.plcoding.bookpedia.presentation.book.detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.plcoding.bookpedia.presentation.DarkBlue
import com.plcoding.bookpedia.presentation.composables.RemoteImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BlurredImageBackground(
    imageUrl: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        RemoteImage(
            imageUrl = imageUrl,
            contentDescription = null,
            contentAlignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(maxHeight = 200.dp)
                .blur(12.dp)
                .background(DarkBlue),
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            content()
        }
    }
}


@Composable
fun BlurredImageBackgroundVersion1(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
        {
            RemoteImage(
                imageUrl = imageUrl,
                contentDescription = null,
                contentAlignment = Alignment.TopCenter,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 200.dp)
                    .blur(12.dp),
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .height(140.dp),
            ) {
                RemoteImage(
                    imageUrl = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .border(2.dp, color = DarkBlue),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBlurredImageBackground() {
    MaterialTheme {
        BlurredImageBackground(
            imageUrl = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%2Fid%2FOIP.p9mQtH__m35NjLk6Wh1i0QHaEK%3Fpid%3DApi&f=1&ipt=4187892f3938fa02af7f6637c91790d48216eb3c56816dc34880be4a4727fbcb&ipo=images",
            content = {},
        )
    }
}
