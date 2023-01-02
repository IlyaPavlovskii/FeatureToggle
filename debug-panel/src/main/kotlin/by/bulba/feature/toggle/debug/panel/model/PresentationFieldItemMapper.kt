package by.bulba.feature.toggle.debug.panel.model

import android.util.Log
import by.bulba.feature.toggle.FeatureToggle
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

internal interface PresentationFieldItemMapper {
    fun convert(featureToggle: FeatureToggle): List<PresentationFieldItem>

    companion object {
        fun create(): PresentationFieldItemMapper = DefaultPresentationFieldItemMapper()
    }
}

private class DefaultPresentationFieldItemMapper(
    private val fieldNameBlacklist: List<String> = DEFAULT_FIELD_NAME_BLACKLIST
) : PresentationFieldItemMapper {

    private val json: Json = Json.Default

    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override fun convert(featureToggle: FeatureToggle): List<PresentationFieldItem> {
        val serializer = featureToggle::class.serializer()
        val jsonObject = json.encodeToJsonElement(
            serializer = serializer as SerializationStrategy<FeatureToggle>,
            value = featureToggle
        ).jsonObject

        return listOf(PresentationFieldItem.Header(title = featureToggle.toggleKey.key)) +
            (0 until serializer.descriptor.elementsCount)
                .mapNotNull { index ->
                    val name = serializer.descriptor.getElementName(index)
                    if (fieldNameBlacklist.contains(name)) return@mapNotNull null
                    val descriptor = serializer.descriptor.getElementDescriptor(index)
                    Log.d(
                        "TAG",
                        "name: $name JC: ${descriptor.kind.javaClass} Val: ${jsonObject[name]}"
                    )
                    when (val kind = descriptor.kind) {
                        PrimitiveKind.LONG -> (jsonObject[name] as? JsonPrimitive)
                            ?.contentOrNull?.toLong()
                            ?.let { value ->
                                PresentationFieldItem.LongType(
                                    title = name,
                                    featureToggleKey = featureToggle.toggleKey,
                                    value = value
                                )
                            }

                        PrimitiveKind.INT -> (jsonObject[name] as? JsonPrimitive)
                            ?.contentOrNull?.toInt()
                            ?.let { value ->
                                PresentationFieldItem.IntType(
                                    title = name,
                                    featureToggleKey = featureToggle.toggleKey,
                                    value = value
                                )
                            }

                        PrimitiveKind.STRING -> (jsonObject[name] as? JsonPrimitive)
                            ?.contentOrNull
                            ?.let { value ->
                                PresentationFieldItem.StringType(
                                    title = name,
                                    featureToggleKey = featureToggle.toggleKey,
                                    value = value
                                )
                            }

                        PrimitiveKind.BOOLEAN -> (jsonObject[name] as? JsonPrimitive)
                            ?.contentOrNull?.toBooleanStrictOrNull()
                            ?.let { value ->
                                PresentationFieldItem.BooleanType(
                                    title = name,
                                    featureToggleKey = featureToggle.toggleKey,
                                    enabled = value
                                )
                            }

                        PrimitiveKind.FLOAT -> (jsonObject[name] as? JsonPrimitive)
                            ?.contentOrNull?.toFloatOrNull()
                            ?.let { value ->
                                PresentationFieldItem.FloatType(
                                    title = name,
                                    featureToggleKey = featureToggle.toggleKey,
                                    value = value
                                )
                            }

                        SerialKind.ENUM -> (jsonObject[name] as? JsonPrimitive)
                            ?.contentOrNull
                            ?.let { value ->
                                val enumValues = (0 until descriptor.elementsCount)
                                    .mapNotNull { enumIndex ->
                                        descriptor.getElementName(enumIndex)
                                    }
                                PresentationFieldItem.EnumType(
                                    title = name,
                                    featureToggleKey = featureToggle.toggleKey,
                                    selectedValue = value,
                                    values = enumValues
                                )
                            }

                        else -> {
                            null
                        }
                    }
                }
    }

    companion object {
        private val DEFAULT_FIELD_NAME_BLACKLIST = listOf("toggleKey", "TOGGLE_KEY")
    }
}