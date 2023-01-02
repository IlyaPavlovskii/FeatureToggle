package by.bulba.feature.toggle.sample.compose

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import by.bulba.feature.toggle.sample.model.MenuItem
import by.bulba.feature.toggle.sample.toggles.MenuItemFeatureToggle

@Composable
internal fun MenuItemVerticalGrid(
    featureToggle: MenuItemFeatureToggle,
    items: List<MenuItem>,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(count = featureToggle.gridCount)) {
        items(items) { item: MenuItem ->
            MenuItemCard(featureToggle = featureToggle, item = item)
        }
    }
}