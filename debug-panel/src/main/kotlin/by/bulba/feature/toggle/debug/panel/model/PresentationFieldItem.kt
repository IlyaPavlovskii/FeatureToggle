package by.bulba.feature.toggle.debug.panel.model

internal sealed class PresentationFieldItem {
    abstract val title: String

    data class Header(
        override val title: String,
    ) : PresentationFieldItem()

    data class BooleanType(
        override val title: String,
        val enabled: Boolean,
    ) : PresentationFieldItem()

    data class IntType(
        override val title: String,
        val value: Int,
    ) : PresentationFieldItem()

    data class LongType(
        override val title: String,
        val value: Long,
    ) : PresentationFieldItem()

    data class FloatType(
        override val title: String,
        val value: Float,
    ) : PresentationFieldItem()

    data class StringType(
        override val title: String,
        val value: String,
    ) : PresentationFieldItem()
}