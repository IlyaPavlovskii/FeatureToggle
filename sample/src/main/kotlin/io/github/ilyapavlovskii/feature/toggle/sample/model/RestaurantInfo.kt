package io.github.ilyapavlovskii.feature.toggle.sample.model

import androidx.annotation.DrawableRes

internal data class RestaurantInfo(
    @DrawableRes val mapImage: Int,
    val deliveryCosts: String,
    val rating: Float,
)