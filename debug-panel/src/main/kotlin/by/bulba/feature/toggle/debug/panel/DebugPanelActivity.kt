package by.bulba.feature.toggle.debug.panel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.bulba.feature.toggle.debug.panel.compose.FieldBooleanType
import by.bulba.feature.toggle.debug.panel.compose.FieldEnumType
import by.bulba.feature.toggle.debug.panel.compose.FieldFloatType
import by.bulba.feature.toggle.debug.panel.compose.FieldHeader
import by.bulba.feature.toggle.debug.panel.compose.FieldIntType
import by.bulba.feature.toggle.debug.panel.compose.FieldLongType
import by.bulba.feature.toggle.debug.panel.compose.FieldStringType
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItem
import by.bulba.feature.toggle.design.system.theme.FeatureToggleTheme

internal class DebugPanelActivity : ComponentActivity() {

    private val viewModel: DebugPanelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeatureToggleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    DebugPanelContent(
                        onResetToDefaultClick = viewModel::resetToDefault,
                        onSaveClick = viewModel::save
                    )
                }
            }
        }
    }

    @Composable
    private fun DebugPanelContent(
        onResetToDefaultClick: () -> Unit,
        onSaveClick: () -> Unit,
    ) {
        val viewState = viewModel.viewState.collectAsState()
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f, false),
            ) {
                items(
                    items = viewState.value.items,
                    itemContent = { item ->
                        when (item) {
                            is PresentationFieldItem.Header -> FieldHeader(item = item)
                            is PresentationFieldItem.IntType -> FieldIntType(item = item)
                            is PresentationFieldItem.FloatType -> FieldFloatType(item = item)
                            is PresentationFieldItem.LongType -> FieldLongType(item = item)
                            is PresentationFieldItem.BooleanType -> FieldBooleanType(item = item)
                            is PresentationFieldItem.StringType -> FieldStringType(item = item)
                            is PresentationFieldItem.EnumType ->
                                FieldEnumType(item = item) { newValue ->
                                    viewModel.selectEnumValue(item, newValue)
                                }
                        }
                    })
            }
            Row {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Transparent,
                        containerColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(16.dp),
                    enabled = viewState.value.resetToDefaultAvailable,
                    onClick = onResetToDefaultClick,
                ) {
                    Text(
                        text = stringResource(id = R.string.debug_panel__reset_to_default),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(
                                bounded = true,
                                color = MaterialTheme.colorScheme.secondary,
                            ),
                            onClick = {},
                        ),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Transparent,
                        containerColor = Color.Transparent,
                    ),
                    enabled = viewState.value.saveAvailable,
                    shape = RoundedCornerShape(16.dp),
                    onClick = onSaveClick,
                ) {
                    Text(
                        text = stringResource(id = R.string.debug_panel__save),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }

    }
}