package by.bulba.feature.toggle.sample

import androidx.lifecycle.ViewModel
import by.bulba.feature.toggle.sample.model.SampleViewState
import by.bulba.feature.toggle.sample.provider.ModelItemProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class MainViewModel(
    modelItemProvider: ModelItemProvider = ModelItemProvider.create(),
) : ViewModel() {
    private val _viewState = MutableStateFlow(
        SampleViewState(
            menuItems = modelItemProvider.getItems()
        )
    )
    val viewState: StateFlow<SampleViewState> = _viewState
}