package io.github.ilyapavlovskii.feature.toggle.sample.toggles

import io.github.ilyapavlovskii.feature.toggle.FeatureToggle
import io.github.ilyapavlovskii.feature.toggle.FeatureToggleKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RestaurantInfoFeatureToggle(
    @SerialName("map_visible") val mapVisible: Boolean,
    @SerialName("delivery_costs_visible") val deliveryCostsVisible: Boolean,
    @SerialName("rating_visible") val ratingVisible: Boolean,
) : FeatureToggle {

    override val toggleKey: FeatureToggleKey = FeatureToggleKey(KEY)

    companion object {
        private const val KEY = "restaurant_info"
    }
}