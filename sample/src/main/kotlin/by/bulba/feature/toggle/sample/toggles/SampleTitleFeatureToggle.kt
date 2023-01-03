package by.bulba.feature.toggle.sample.toggles

import by.bulba.feature.toggle.FeatureToggle
import by.bulba.feature.toggle.FeatureToggleKey
import kotlinx.serialization.Serializable

@Serializable
internal data class SampleTitleFeatureToggle(
    val enabled: Boolean,
    val title: String,
) : FeatureToggle {
    override val toggleKey: FeatureToggleKey = FeatureToggleKey(KEY)

    companion object {
        private const val KEY = "sample_title"
    }
}