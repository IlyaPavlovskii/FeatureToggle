package by.bulba.feature.toggle.debug.panel.model

internal data class DebugPanelViewState(
    val resetToDefaultAvailable: Boolean,
    val saveAvailable: Boolean,
    val items: List<PresentationFieldItem>,
) {
    companion object {
        val DEFAULT = DebugPanelViewState(
            resetToDefaultAvailable = true,
            saveAvailable = true,
            items = emptyList(),
        )
    }
}