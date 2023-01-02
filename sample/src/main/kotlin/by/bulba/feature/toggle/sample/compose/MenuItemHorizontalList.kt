package by.bulba.feature.toggle.sample.compose

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import by.bulba.feature.toggle.sample.model.MenuItem
import by.bulba.feature.toggle.sample.toggles.MenuItemFeatureToggle

@Composable
internal fun MenuItemHorizontalList(
    featureToggle: MenuItemFeatureToggle,
    items: List<MenuItem>,
) {
    LazyRow {
        items(items) { item: MenuItem ->
            MenuItemCard(featureToggle = featureToggle, item = item)
        }
    }

}