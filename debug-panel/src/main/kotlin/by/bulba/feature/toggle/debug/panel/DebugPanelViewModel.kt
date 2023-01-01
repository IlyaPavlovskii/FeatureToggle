package by.bulba.feature.toggle.debug.panel

import androidx.lifecycle.ViewModel
import by.bulba.feature.toggle.FeatureToggleContainer
import by.bulba.feature.toggle.FeatureToggleContainerHolder
import by.bulba.feature.toggle.debug.panel.model.DebugPanelViewState
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItem
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItemMapper
import by.bulba.feature.toggle.debug.panel.utils.mutate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class DebugPanelViewModel(
    private val fieldItemMapper: PresentationFieldItemMapper = PresentationFieldItemMapper.create(),
    featureToggleContainer: FeatureToggleContainer = FeatureToggleContainerHolder.getContainer(),
) : ViewModel() {
    private val _viewState = MutableStateFlow(DebugPanelViewState.DEFAULT)
    val viewState: StateFlow<DebugPanelViewState> = _viewState

    init {
        val items = featureToggleContainer.getFeatureToggles().flatMap(fieldItemMapper::convert)
        _viewState.value = _viewState.value.copy(items = items + items + items)
    }

    fun selectEnumValue(item: PresentationFieldItem.EnumType, newValue: String) {
        _viewState.mutate {
            this.copy(
                items = this.items.map {fieldItem ->
                    if (fieldItem == item) {
                        item.copy(selectedValue = newValue)
                    } else {
                        fieldItem
                    }
                }
            )
        }
    }

    fun resetToDefault() {
        // TODO
    }

    fun save() {
        // TODO
    }
}


