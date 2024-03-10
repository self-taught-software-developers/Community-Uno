package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.component.helpers.StyleGuide

@Composable
fun PlayerColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = { }
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(StyleGuide.Spacing.value)
    ) { content() }
}