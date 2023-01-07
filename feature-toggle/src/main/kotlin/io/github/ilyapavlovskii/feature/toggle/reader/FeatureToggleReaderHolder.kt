package io.github.ilyapavlovskii.feature.toggle.reader

object FeatureToggleReaderHolder {
    private var featureToggleReader: FeatureToggleReader? = null

    fun init(reader: FeatureToggleReader) {
        featureToggleReader = reader
    }

    fun getFeatureToggleReader(): FeatureToggleReader =
        requireNotNull(featureToggleReader) {
            "Feature toggle reader must be initialized before usage."
        }
}