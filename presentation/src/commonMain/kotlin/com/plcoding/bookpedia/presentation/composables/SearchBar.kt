package com.plcoding.bookpedia.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import cmp_bookpedia.presentation.generated.resources.Res
import cmp_bookpedia.presentation.generated.resources.search_clear
import cmp_bookpedia.presentation.generated.resources.search_hint
import com.plcoding.bookpedia.presentation.DarkBlue
import com.plcoding.bookpedia.presentation.DesertWhite
import com.plcoding.bookpedia.presentation.RoundedShape
import com.plcoding.bookpedia.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearchAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow,
            backgroundColor = SandYellow,
        )
    ) {
        val focusRequester = remember { FocusRequester() }
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            singleLine = true,
            shape = RoundedShape,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = DarkBlue,
                focusedBorderColor = SandYellow,
            ),
            placeholder = { Text(text = stringResource(Res.string.search_hint)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = .50f)
                )
            },
            keyboardActions = KeyboardActions(onSearch = { onImeSearchAction() }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            trailingIcon = {
                AnimatedVisibility(visible = searchQuery.isNotBlank()) {
                    IconButton(
                        onClick = {
                            onSearchQueryChange("")
                            focusRequester.requestFocus()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(Res.string.search_clear),
                        )
                    }
                }
            },
            modifier = modifier
                .minimumInteractiveComponentSize()
                .focusRequester(focusRequester)
                .background(
                    shape = RoundedShape,
                    color = DesertWhite,
                )
        )
    }
}

@Preview
@Composable
private fun SearchBarWithText() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SearchBar(
                searchQuery = "test query",
                onSearchQueryChange = {},
                onImeSearchAction = {},
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarEmpty() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SearchBar(
                searchQuery = "",
                onSearchQueryChange = {},
                onImeSearchAction = {},
            )
        }
    }
}
