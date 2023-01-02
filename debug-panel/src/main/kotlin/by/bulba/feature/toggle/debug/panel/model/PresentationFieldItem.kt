package by.bulba.feature.toggle.debug.panel.model

import by.bulba.feature.toggle.FeatureToggleKey

internal sealed class PresentationFieldItem {
    abstract val title: String

    data class Header(
        override val title: String,
    ) : PresentationFieldItem()

    data class BooleanType(
        override val title: String,
        override val featureToggleKey: FeatureToggleKey,
        val enabled: Boolean,
    ) : PresentationFieldItem(), FeatureToggleKeyOwner

    data class IntType(
        override val title: String,
        override val featureToggleKey: FeatureToggleKey,
        val value: Int,
    ) : PresentationFieldItem(), FeatureToggleKeyOwner

    data class LongType(
        override val title: String,
        override val featureToggleKey: FeatureToggleKey,
        val value: Long,
    ) : PresentationFieldItem(), FeatureToggleKeyOwner

    data class FloatType(
        override val title: String,
        override val featureToggleKey: FeatureToggleKey,
        val value: Float,
    ) : PresentationFieldItem(), FeatureToggleKeyOwner

    data class StringType(
        override val title: String,
        override val featureToggleKey: FeatureToggleKey,
        val value: String,
    ) : PresentationFieldItem(), FeatureToggleKeyOwner

    data class EnumType(
        override val title: String,
        override val featureToggleKey: FeatureToggleKey,
        val selectedValue: String,
        val values: List<String>,
    ) : PresentationFieldItem(), FeatureToggleKeyOwner
}