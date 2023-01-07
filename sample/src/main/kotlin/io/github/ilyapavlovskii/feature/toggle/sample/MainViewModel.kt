package io.github.ilyapavlovskii.feature.toggle.sample

import androidx.lifecycle.ViewModel
import io.github.ilyapavlovskii.feature.toggle.sample.model.RestaurantInfo
import io.github.ilyapavlovskii.feature.toggle.sample.model.SampleViewState
import io.github.ilyapavlovskii.feature.toggle.sample.provider.ModelItemProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import io.github.ilyapavlovskii.feature.toggle.sample.R

internal class MainViewModel(
    modelItemProvider: ModelItemProvider = ModelItemProvider.create(),
) : ViewModel() {
    private val _viewState = MutableStateFlow(
        SampleViewState(
            restaurantInfo = RestaurantInfo(
                mapImage = R.drawable.map_image,
                deliveryCosts = "3.5 $",
                rating = 4.4f,
            ),
            menuItems = modelItemProvider.getItems()
        )
    )
    val viewState: StateFlow<SampleViewState> = _viewState
}