package io.github.ilyapavlovskii.feature.toggle.debug.panel.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.ilyapavlovskii.feature.toggle.debug.panel.model.PresentationFieldItem

@Composable
internal fun FieldEnumType(
    item: PresentationFieldItem.EnumType,
    selectItem: (newValue: String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = fieldModifier) {
        Text(text = item.title)
        Box {
            Text(
                text = item.selectedValue,
                modifier = Modifier.fillMaxWidth()
                    .clickable(onClick = { expanded = true })
                    .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary))
                    .padding(8.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                item.values.forEach { value ->
                    DropdownMenuItem(
                        text = {
                            Text(text = value)
                        },
                        onClick = {
                            selectItem(value)
                            expanded = false
                        })
                }
            }
        }
    }
}