package io.github.ilyapavlovskii.feature.toggle

object FeatureToggleContainerHolder {

    private var container: FeatureToggleContainer? = null

    fun init(container: FeatureToggleContainer) {
        FeatureToggleContainerHolder.container = container
    }

    fun getContainer(): FeatureToggleContainer =
        requireNotNull(container) {
            "Feature toggle container must be initialized before usage."
        }
}