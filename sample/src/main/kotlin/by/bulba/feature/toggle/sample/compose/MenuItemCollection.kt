package by.bulba.feature.toggle.sample.compose

import androidx.compose.runtime.Composable
import by.bulba.feature.toggle.sample.model.MenuItem
import by.bulba.feature.toggle.sample.toggles.MenuItemFeatureToggle

@Composable
internal fun MenuItemCollection(featureToggle: MenuItemFeatureToggle, items: List<MenuItem>) {
    when (featureToggle.type) {
        MenuItemFeatureToggle.PreviewType.GRID ->
            MenuItemVerticalGrid(featureToggle = featureToggle, items = items)

        MenuItemFeatureToggle.PreviewType.VERTICAL_LIST ->
            MenuItemVerticalList(featureToggle = featureToggle, items = items)

        MenuItemFeatureToggle.PreviewType.HORIZONTAL_LIST ->
            MenuItemHorizontalList(featureToggle = featureToggle, items = items)

    }
}