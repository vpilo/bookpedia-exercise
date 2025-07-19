package com.plcoding.bookpedia.presentation.book.detail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.plcoding.bookpedia.presentation.BookChipSizeNormal
import com.plcoding.bookpedia.presentation.BookChipSizeSmall
import com.plcoding.bookpedia.presentation.DefaultPadding
import com.plcoding.bookpedia.presentation.LightBlue
import com.plcoding.bookpedia.presentation.RoundedShapeCornerSizeSmall

enum class BookChipSize {
    Small,
    Normal,
}

@Composable
fun BookChip(
    size: BookChipSize = BookChipSize.Normal,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .widthIn(
                min = when(size) {
                    BookChipSize.Small -> BookChipSizeSmall
                    BookChipSize.Normal -> BookChipSizeNormal
                }
            )
            .clip(RoundedCornerShape(RoundedShapeCornerSizeSmall))
            .background(LightBlue)
            .padding(DefaultPadding.Small),
    ) {
        content()
    }
}
