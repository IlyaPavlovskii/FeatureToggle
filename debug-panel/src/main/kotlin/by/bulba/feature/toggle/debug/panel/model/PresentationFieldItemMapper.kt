package by.bulba.feature.toggle.debug.panel.model

import android.util.Log
import by.bulba.feature.toggle.FeatureToggle
import java.lang.reflect.Field

internal interface PresentationFieldItemMapper {
    fun convert(featureToggle: FeatureToggle): List<PresentationFieldItem>

    companion object {
        fun create(): PresentationFieldItemMapper = DefaultPresentationFieldItemMapper()
    }
}

private class DefaultPresentationFieldItemMapper(
    private val fieldNameBlacklist: List<String> = DEFAULT_FIELD_NAME_BLACKLIST
) : PresentationFieldItemMapper {
    override fun convert(featureToggle: FeatureToggle): List<PresentationFieldItem> {
        return listOf(PresentationFieldItem.Header(title = featureToggle.toggleKey.key)) +
            featureToggle.javaClass.declaredFields
                .filter { field -> !fieldNameBlacklist.contains(field.name) }
                .mapNotNull { field ->
                    field.isAccessible = true
                    val value = field[featureToggle]
                    Log.d("TAG", "field: ${field.name} " +
                        "Type: ${field.type.componentType} " +
                        "GT: ${field.genericType} " +
                        "Value: $value")
                    field.toPresentationFieldItem(value)
                }
    }

    private fun Field.toPresentationFieldItem(value: Any): PresentationFieldItem? {
        return when (this.genericType) {
            Long::class.java -> PresentationFieldItem.LongType(
                title = this.name,
                value = value as Long
            )

            Int::class.java -> PresentationFieldItem.IntType(
                title = this.name,
                value = value as Int
            )

            String::class.java -> PresentationFieldItem.StringType(
                title = this.name,
                value = value as String,
            )

            Float::class.java -> PresentationFieldItem.FloatType(
                title = this.name,
                value = value as Float,
            )

            Boolean::class.java -> PresentationFieldItem.BooleanType(
                title = this.name,
                enabled = value as Boolean,
            )

            else -> null
        }
    }

    companion object {
        private val DEFAULT_FIELD_NAME_BLACKLIST = listOf("toggleKey", "TOGGLE_KEY")
    }
}