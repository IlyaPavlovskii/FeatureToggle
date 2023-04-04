package io.github.ilyapavlovskii.feature.toggle.debug.panel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.ilyapavlovskii.feature.toggle.debug.panel.R
import io.github.ilyapavlovskii.feature.toggle.debug.panel.compose.FieldBooleanType
import io.github.ilyapavlovskii.feature.toggle.debug.panel.compose.FieldEnumType
import io.github.ilyapavlovskii.feature.toggle.debug.panel.compose.FieldFloatType
import io.github.ilyapavlovskii.feature.toggle.debug.panel.compose.FieldHeader
import io.github.ilyapavlovskii.feature.toggle.debug.panel.compose.FieldIntType
import io.github.ilyapavlovskii.feature.toggle.debug.panel.compose.FieldLongType
import io.github.ilyapavlovskii.feature.toggle.debug.panel.compose.FieldStringType
import io.github.ilyapavlovskii.feature.toggle.debug.panel.model.DebugPanelViewState
import io.github.ilyapavlovskii.feature.toggle.debug.panel.model.PresentationFieldItem
import io.github.ilyapavlovskii.feature.toggle.design.system.theme.FeatureToggleTheme

class DebugPanelActivity : ComponentActivity() {

    private val viewModel: DebugPanelViewModel by viewModels {
        DebugPanelViewModel.factory(
            applicationContext = this@DebugPanelActivity.applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeatureToggleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val viewState = viewModel.viewState.collectAsState()
                    val value = viewState.value
                    if (value.isLoading()) {
                        DebugPanelLoading()
                    } else {
                        DebugPanelContent(
                            viewState = value,
                            onResetToDefaultClick = viewModel::dropConfig,
                            onSaveClick = viewModel::save,
                            onDismissDialogClick = viewModel::dismissDialog,
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun DebugPanelLoading() {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    private fun DebugPanelContent(
        viewState: DebugPanelViewState,
        onResetToDefaultClick: () -> Unit,
        onSaveClick: () -> Unit,
        onDismissDialogClick: () -> Unit,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight()
                    .weight(1f, false),
            ) {
                items(
                    items = viewState.items,
                    itemContent = { item ->
                        when (item) {
                            is PresentationFieldItem.Header -> FieldHeader(item = item)
                            is PresentationFieldItem.IntType -> FieldIntType(item = item) { newValue ->
                                viewModel.updateIntValue(oldItem = item, newValue = newValue)
                            }

                            is PresentationFieldItem.FloatType -> FieldFloatType(item = item) { newValue ->
                                viewModel.updateFloatValue(oldItem = item, newValue = newValue)
                            }

                            is PresentationFieldItem.LongType -> FieldLongType(item = item) { newValue ->
                                viewModel.updateLongValue(oldItem = item, newValue = newValue)
                            }

                            is PresentationFieldItem.BooleanType ->
                                FieldBooleanType(item = item) { newValue ->
                                    viewModel.updateBooleanValue(
                                        oldItem = item,
                                        newValue = newValue
                                    )
                                }

                            is PresentationFieldItem.StringType ->
                                FieldStringType(item = item) { newValue ->
                                    viewModel.updateStringValue(oldItem = item, newValue = newValue)
                                }

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
                    enabled = viewState.dropConfigAvailable,
                    onClick = onResetToDefaultClick,
                ) {
                    Text(
                        text = stringResource(id = R.string.debug_panel__drop_config),
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
                    enabled = viewState.saveAvailable,
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
        viewState.dialog?.also {dialog ->
            AlertDialog(
                title = {
                    Text(text = stringResource(id = dialog.title))
                },
                text = {
                    Text(text = stringResource(id = dialog.message))
                },
                confirmButton = {
                    Text(
                        modifier = Modifier.clickable { onDismissDialogClick() },
                        text = stringResource(id = dialog.button)
                    )
                },
                onDismissRequest = onDismissDialogClick,
            )
        }
    }
}