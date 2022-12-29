package by.bulba.feature.toggle.debug.panel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import by.bulba.feature.toggle.debug.panel.compose.FieldBooleanType
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
                Surface(modifier = Modifier.fillMaxSize(),
                ) {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(
                            items = viewModel.viewState.value.items,
                            itemContent = { item ->
                                when(item) {
                                    is PresentationFieldItem.Header -> FieldHeader(item = item)
                                    is PresentationFieldItem.IntType -> FieldIntType(item = item)
                                    is PresentationFieldItem.FloatType -> FieldFloatType(item = item)
                                    is PresentationFieldItem.LongType -> FieldLongType(item = item)
                                    is PresentationFieldItem.BooleanType -> FieldBooleanType(item = item)
                                    is PresentationFieldItem.StringType -> FieldStringType(item = item)
                                }
                        })
                    }
                }
            }
        }
    }
}