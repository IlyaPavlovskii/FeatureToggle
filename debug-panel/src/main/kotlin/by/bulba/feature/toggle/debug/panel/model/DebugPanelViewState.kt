package by.bulba.feature.toggle.debug.panel.model

internal sealed class DebugPanelViewState {

    object Loading : DebugPanelViewState()

    data class Content(
        val resetToDefaultAvailable: Boolean,
        val saveAvailable: Boolean,
        val items: List<PresentationFieldItem>,
    ) : DebugPanelViewState()
}