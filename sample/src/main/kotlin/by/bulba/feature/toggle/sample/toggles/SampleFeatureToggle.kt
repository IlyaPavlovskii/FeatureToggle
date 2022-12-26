package by.bulba.feature.toggle.sample.toggles

import by.bulba.feature.toggle.FeatureToggle
import by.bulba.feature.toggle.FeatureToggleKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class SampleFeatureToggle(
    @SerialName("button_color") val buttonColor: Long,
    @SerialName("type") val type: Type,
) : FeatureToggle {
    override val toggleKey: FeatureToggleKey = TOGGLE_KEY

    @Serializable
    enum class Type {
        @SerialName("active")
        ACTIVE,

        @SerialName("inactive")
        INACTIVE,

        @SerialName("hidden")
        HIDDEN,
        ;
    }

    companion object {
        private val TOGGLE_KEY = FeatureToggleKey("sample_feature_toggle")
    }
}