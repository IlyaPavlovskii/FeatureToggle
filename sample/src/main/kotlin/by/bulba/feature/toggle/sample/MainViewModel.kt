package by.bulba.feature.toggle.sample

import androidx.lifecycle.ViewModel
import by.bulba.feature.toggle.sample.model.RestaurantInfo
import by.bulba.feature.toggle.sample.model.SampleViewState
import by.bulba.feature.toggle.sample.provider.ModelItemProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import by.bulba.feature.toggle.sample.R

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