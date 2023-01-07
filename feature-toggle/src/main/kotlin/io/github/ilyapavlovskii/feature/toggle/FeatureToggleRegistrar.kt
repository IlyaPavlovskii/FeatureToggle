package io.github.ilyapavlovskii.feature.toggle

import io.github.ilyapavlovskii.feature.toggle.reader.FeatureToggleReader
import io.github.ilyapavlovskii.feature.toggle.reader.FeatureToggleReaderHolder
import kotlin.reflect.KClass

/**
 * Common place for register [FeatureToggle] and setup feature values.
 * */
class FeatureToggleRegistrar(
    featureToggleContainer: FeatureToggleContainer = FeatureToggleContainerHolder.getContainer(),
    private val reader: FeatureToggleReader = FeatureToggleReaderHolder.getFeatureToggleReader(),
) : FeatureToggleProvider {

    private var featuresMap = hashMapOf<KClass<out FeatureToggle>, FeatureToggle>()
    private var initialized: Boolean = false

    init {
        featureToggleContainer.getFeatureToggles()
            .map { featureToggle -> featureToggle::class to featureToggle }
            .toMap(featuresMap)
    }

    /**
     * Setup feature values. Load [FeatureToggle] values from config via [FeatureToggleReader].
     * Must be called once.
     * */
    fun setupFeatures(): FeatureToggleRegistrar {
        require(!initialized) { "Features already initialized" }
        featuresMap.forEach { entry ->
            val key = entry.key
            val feature = entry.value
            reader.readFeature(feature)?.also {
                featuresMap[key] = it
            }
        }
        initialized = true
        return this
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : FeatureToggle> get(clazz: KClass<T>): T {
        require(initialized) { "Features have to be initialized before use" }
        require(this.featuresMap.contains(clazz)) { "Feature $clazz isn't registered" }
        return featuresMap[clazz] as T
    }
}
