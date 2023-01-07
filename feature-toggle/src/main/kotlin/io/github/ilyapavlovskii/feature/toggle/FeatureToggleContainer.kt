package io.github.ilyapavlovskii.feature.toggle

interface FeatureToggleContainer {
    fun getFeatureToggles(): Set<FeatureToggle>
}

class SimpleFeatureToggleContainer(
    private val featureToggles: Set<FeatureToggle>,
) : FeatureToggleContainer {
    override fun getFeatureToggles(): Set<FeatureToggle> = featureToggles
}