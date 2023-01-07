package io.github.ilyapavlovskii.feature.toggle.debug.panel.util

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.mutate(block: T.() -> T) {
    this.value = block(this.value)
}