package by.bulba.feature.toggle.sample.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class MenuItem(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val price: String,
)