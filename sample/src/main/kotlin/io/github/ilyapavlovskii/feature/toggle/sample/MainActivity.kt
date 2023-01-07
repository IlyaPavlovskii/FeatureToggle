package io.github.ilyapavlovskii.feature.toggle.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import io.github.ilyapavlovskii.feature.toggle.design.system.theme.FeatureToggleTheme
import io.github.ilyapavlovskii.feature.toggle.get
import io.github.ilyapavlovskii.feature.toggle.sample.compose.MenuItemCollection
import io.github.ilyapavlovskii.feature.toggle.toggles.FeatureToggleRegistrarHolder
import io.github.ilyapavlovskii.feature.toggle.sample.toggles.MenuItemFeatureToggle
import io.github.ilyapavlovskii.feature.toggle.sample.toggles.RestaurantInfoFeatureToggle
import io.github.ilyapavlovskii.feature.toggle.sample.toggles.SampleTitleFeatureToggle

internal class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val featureToggleProvider = FeatureToggleRegistrarHolder.featureToggleProvider()
        val sampleTitleFeatureToggle: SampleTitleFeatureToggle = featureToggleProvider.get()
        val restaurantInfoFeatureToggle = featureToggleProvider.get<RestaurantInfoFeatureToggle>()
        val menuItemFeatureToggle = featureToggleProvider.get<MenuItemFeatureToggle>()

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
                        if (restaurantInfoFeatureToggle.mapVisible) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Restaurant address",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                bitmap = ImageBitmap.imageResource(
                                    id = viewState.value.restaurantInfo.mapImage
                                ),
                                contentScale = ContentScale.Inside,
                                contentDescription = "Image description"
                            )
                        }
                        if (restaurantInfoFeatureToggle.deliveryCostsVisible) {
                            Row(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = "Delivery cost: ",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Text(
                                    text = viewState.value.restaurantInfo.deliveryCosts
                                )
                            }
                        }
                        if (restaurantInfoFeatureToggle.ratingVisible) {
                            Row(modifier = Modifier.padding(8.dp)) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        id = R.drawable.baseline_star_24
                                    ),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "Restaurant rating"
                                )
                                Text(
                                    text = viewState.value.restaurantInfo.rating.toString()
                                )
                            }
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
