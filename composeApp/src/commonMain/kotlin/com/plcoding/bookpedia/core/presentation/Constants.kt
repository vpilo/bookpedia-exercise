package com.plcoding.bookpedia.core.presentation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val MaxUserInterfaceWidth: Dp = 500.dp

object DefaultPadding {
    val Tiny: Dp = 4.dp
    val Small: Dp = 8.dp
    val Medium: Dp = 16.dp
}

val DefaultButtonSize: Dp = 40.dp

val DefaultThumbnailSize: Dp = 96.dp

val RoundedShape: Shape
    get() = RoundedCornerShape(32.dp)
