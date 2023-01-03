package by.bulba.feature.toggle.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.bulba.feature.toggle.design.system.theme.FeatureToggleTheme
import by.bulba.feature.toggle.get
import by.bulba.feature.toggle.sample.compose.MenuItemCollection
import by.bulba.feature.toggle.sample.toggles.FeatureToggleRegistrarHolder
import by.bulba.feature.toggle.sample.toggles.MenuItemFeatureToggle
import by.bulba.feature.toggle.sample.toggles.SampleTitleFeatureToggle

internal class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val menuItemFeatureToggle = FeatureToggleRegistrarHolder.featureToggleProvider()
            .get<MenuItemFeatureToggle>()
        val sampleTitleFeatureToggle = FeatureToggleRegistrarHolder.featureToggleProvider()
            .get<SampleTitleFeatureToggle>()
        setContent {
            FeatureToggleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val viewState = viewModel.viewState.collectAsState()
                    Column {
                        if (sampleTitleFeatureToggle.enabled) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = sampleTitleFeatureToggle.title,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                        if (menuItemFeatureToggle.enabled) {
                            MenuItemCollection(
                                featureToggle = menuItemFeatureToggle,
                                items = viewState.value.menuItems
                            )
                        }
                    }
                }
            }
        }
    }

}
