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

    private fun Field.toPresentationFieldItem(value: Any): PresentationFieldItem? = when {
        this.genericType == Long::class.java -> PresentationFieldItem.LongType(
            title = this.name,
            value = value as Long
        )

        this.genericType == Int::class.java -> PresentationFieldItem.IntType(
            title = this.name,
            value = value as Int
        )

        this.genericType == String::class.java -> PresentationFieldItem.StringType(
            title = this.name,
            value = value as String,
        )

        this.genericType == Float::class.java -> PresentationFieldItem.FloatType(
            title = this.name,
            value = value as Float,
        )

        this.genericType == Boolean::class.java -> PresentationFieldItem.BooleanType(
            title = this.name,
            enabled = value as Boolean,
        )

        this.type.isEnum && this.type.enumConstants.isNotEmpty() -> PresentationFieldItem.EnumType(
            title = this.name,
            values = this.type.enumConstants.mapNotNull { (it as? Enum<*>)?.name },
            selectedValue = (value as Enum<*>).name
        )

        else -> null
    }

    companion object {
        private val DEFAULT_FIELD_NAME_BLACKLIST = listOf("toggleKey", "TOGGLE_KEY")
    }
}