package by.bulba.feature.toggle

object FeatureToggleContainerHolder {

    private var container: FeatureToggleContainer? = null

    fun init(container: FeatureToggleContainer) {
        this.container = container
    }

    fun getContainer(): FeatureToggleContainer =
        requireNotNull(container) {
            "Feature toggle container must be initialized before usage."
        }
}