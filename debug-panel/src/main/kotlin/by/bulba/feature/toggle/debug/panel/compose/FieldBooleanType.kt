package by.bulba.feature.toggle.debug.panel.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItem

@Composable
internal fun FieldBooleanType(
    item: PresentationFieldItem.BooleanType,
    onCheckedChange: (newValue: Boolean) -> Unit,
) {
    Row(modifier = fieldModifier) {
        Checkbox(
            checked = item.enabled,
            onCheckedChange = onCheckedChange,
        )
        Text(text = item.title, modifier = Modifier.align(alignment = Alignment.CenterVertically))
    }
}