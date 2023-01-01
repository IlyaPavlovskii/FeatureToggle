package by.bulba.feature.toggle.debug.panel.utils

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.mutate(block: T.() -> T) {
    this.value = block(this.value)
}