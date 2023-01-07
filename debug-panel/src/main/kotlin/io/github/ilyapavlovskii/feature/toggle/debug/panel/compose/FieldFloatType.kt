package io.github.ilyapavlovskii.feature.toggle.debug.panel.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import io.github.ilyapavlovskii.feature.toggle.debug.panel.model.PresentationFieldItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FieldFloatType(
    item: PresentationFieldItem.FloatType,
    onValueChange: (newValue: Float) -> Unit,
) {
    Column(modifier = fieldModifier) {
        Text(text = item.title)
        TextField(
            value = item.value.toString(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { newValue ->
                runCatching { newValue.toFloat() }.onSuccess(onValueChange)
            }
        )
    }
}