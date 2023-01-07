package io.github.ilyapavlovskii.feature.toggle.reader

import io.github.ilyapavlovskii.feature.toggle.FeatureToggle

/**
 * [FeatureToggleReader] chain of responsibility implementation.
 * */
class ChainFeatureToggleReader(
    private vararg val featureReaders: FeatureToggleReader
) : FeatureToggleReader {

    override fun readFeature(feature: FeatureToggle): FeatureToggle? =
        featureReaders.firstNotNullOfOrNull { reader -> reader.readFeature(feature) }
}
