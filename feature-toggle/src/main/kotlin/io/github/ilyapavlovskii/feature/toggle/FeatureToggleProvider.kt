package io.github.ilyapavlovskii.feature.toggle

import kotlin.reflect.KClass

/**
 * [FeatureToggle] provider.
 * */
interface FeatureToggleProvider {
    fun <T : FeatureToggle> get(clazz: KClass<T>): T
}

inline fun <reified T : FeatureToggle> FeatureToggleProvider.get(): T = get(T::class)
