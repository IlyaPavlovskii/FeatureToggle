package by.bulba.feature.toggle.reader

import by.bulba.feature.toggle.FeatureToggle

/**
 * [FeatureToggle] configuration reader.
 * */
interface FeatureToggleReader {
    /**
     * Read [FeatureToggle] instance from config file.
     *
     * @param feature feature toggle for deserialization
     * */
    fun readFeature(feature: FeatureToggle): FeatureToggle?
}
