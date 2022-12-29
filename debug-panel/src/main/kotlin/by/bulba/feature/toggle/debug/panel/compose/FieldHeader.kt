package by.bulba.feature.toggle.debug.panel.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItem

@Composable
internal fun FieldHeader(item: PresentationFieldItem.Header) {
    Text(
        text = item.title,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineLarge,
    )
}