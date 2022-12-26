package by.bulba.feature.toggle

import kotlinx.serialization.Serializable

/**
 * Feature toggle name.
 * */
@JvmInline
@Serializable
value class FeatureToggleKey(val key: String)
