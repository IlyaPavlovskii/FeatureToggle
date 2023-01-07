package io.github.ilyapavlovskii.feature.toggle.debug.panel.model

import androidx.annotation.StringRes

internal data class DebugPanelViewState(
    val dropConfigAvailable: Boolean,
    val saveAvailable: Boolean,
    val items: List<PresentationFieldItem>,
    val dialog: Dialog? = null,
) {
    fun isLoading(): Boolean = items.isEmpty()

    data class Dialog(
        @StringRes val title: Int,
        @StringRes val message: Int,
        @StringRes val button: Int,
    )

    companion object {
        val DEFAULT = DebugPanelViewState(
            dropConfigAvailable = false,
            saveAvailable = false,
            items = emptyList(),
        )
    }
}