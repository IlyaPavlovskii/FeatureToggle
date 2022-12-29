package by.bulba.feature.toggle.debug.panel.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItem

@Composable
internal fun FieldBooleanType(item: PresentationFieldItem.BooleanType) {
    Row(modifier = fieldModifier) {
        Checkbox(
            checked = item.enabled,
            onCheckedChange = { newValue ->
                Log.d("FieldLongType", "NewValue: $newValue")
            },
        )
        Text(text = item.title, modifier = Modifier.align(alignment = Alignment.CenterVertically))
    }
}