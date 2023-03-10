package io.github.ilyapavlovskii.feature.toggle.sample.toggles

import io.github.ilyapavlovskii.feature.toggle.FeatureToggle
import io.github.ilyapavlovskii.feature.toggle.FeatureToggleKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuItemFeatureToggle(
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("type") val type: PreviewType,
    @SerialName("grid_count") val gridCount: Int,
    @SerialName("add_to_cart_available") val addToCartAvailable: Boolean,
) : FeatureToggle {
    override val toggleKey: FeatureToggleKey = FeatureToggleKey(KEY)

    enum class PreviewType {
        GRID,
        VERTICAL_LIST,
        HORIZONTAL_LIST,
        ;
    }

    companion object {
        private const val KEY = "menu_item"
    }
}