package by.bulba.feature.toggle.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import by.bulba.feature.toggle.design.system.theme.FeatureToggleTheme
import by.bulba.feature.toggle.get
import by.bulba.feature.toggle.sample.compose.MenuItemCollection
import by.bulba.feature.toggle.sample.toggles.FeatureToggleRegistrarHolder
import by.bulba.feature.toggle.sample.toggles.MenuItemFeatureToggle

internal class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val featureToggle = FeatureToggleRegistrarHolder.featureToggleProvider()
            .get<MenuItemFeatureToggle>()
        setContent {
            FeatureToggleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val viewState = viewModel.viewState.collectAsState()
                    if (featureToggle.enabled) {
                        MenuItemCollection(
                            featureToggle = featureToggle,
                            items = viewState.value.menuItems
                        )
                    }
                }
            }
        }
    }

}
