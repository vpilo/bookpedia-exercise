package com.plcoding.bookpedia.core.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.rating
import com.plcoding.bookpedia.core.presentation.DefaultPadding
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Composable
fun Rating(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = stringResource(Res.string.rating),
            tint = SandYellow,
            modifier = Modifier
                .size(MaterialTheme.typography.bodySmall.lineHeight.value.dp)
        )
        Spacer(modifier = Modifier.size(DefaultPadding.Tiny))
        Text(
            text = "${(rating * 10).roundToInt() / 10.0}",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview
@Composable
private fun PreviewRating() {
    MaterialTheme {
        Rating(rating = 3.5)
    }
}
