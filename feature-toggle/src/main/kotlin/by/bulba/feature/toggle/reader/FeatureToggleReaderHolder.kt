package by.bulba.feature.toggle.reader

object FeatureToggleReaderHolder {
    private var featureToggleReader: FeatureToggleReader? = null

    fun init(reader: FeatureToggleReader) {
        this.featureToggleReader = reader
    }

    fun getFeatureToggleReader(): FeatureToggleReader =
        requireNotNull(featureToggleReader) {
            "Feature toggle reader must be initialized before usage."
        }
}