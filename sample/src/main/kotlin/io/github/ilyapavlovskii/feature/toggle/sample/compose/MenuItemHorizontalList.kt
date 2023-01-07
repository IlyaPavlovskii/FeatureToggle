package io.github.ilyapavlovskii.feature.toggle.sample.compose

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import io.github.ilyapavlovskii.feature.toggle.sample.model.MenuItem
import io.github.ilyapavlovskii.feature.toggle.sample.toggles.MenuItemFeatureToggle

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