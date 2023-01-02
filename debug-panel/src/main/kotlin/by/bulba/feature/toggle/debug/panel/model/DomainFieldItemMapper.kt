package by.bulba.feature.toggle.debug.panel.model

import by.bulba.feature.toggle.FeatureToggle
import by.bulba.feature.toggle.FeatureToggleContainer
import by.bulba.feature.toggle.debug.panel.domain.SaveOverrideFieldsInput
import org.json.JSONObject

internal interface DomainFieldItemMapper {
    fun convert(items: List<PresentationFieldItem>): SaveOverrideFieldsInput

    companion object {
        fun create(
            featureToggleContainer: FeatureToggleContainer
        ): DomainFieldItemMapper = DefaultDomainFieldItemMapper(featureToggleContainer)
    }
}

private class DefaultDomainFieldItemMapper(
    private val featureToggleContainer: FeatureToggleContainer,
) : DomainFieldItemMapper {
    override fun convert(items: List<PresentationFieldItem>): SaveOverrideFieldsInput =
        featureToggleContainer.getFeatureToggles()
            .map(FeatureToggle::toggleKey)
            .associateWith { featureToggleKey ->
                items
                    .filterIsInstance<FeatureToggleKeyOwner>()
                    .filter { featureToggleKeyOwner ->
                        featureToggleKeyOwner.featureToggleKey == featureToggleKey
                    }.map { featureToggleKeyOwner ->
                        featureToggleKeyOwner as PresentationFieldItem
                    }.let { fieldItems ->
                        val jsonObject = JSONObject()
                        fieldItems.forEach { presentationFieldItem ->
                            when(presentationFieldItem) {
                                is PresentationFieldItem.BooleanType ->
                                    jsonObject.put(presentationFieldItem.title, presentationFieldItem.enabled)
                                is PresentationFieldItem.EnumType ->
                                    jsonObject.put(presentationFieldItem.title, presentationFieldItem.selectedValue)
                                is PresentationFieldItem.FloatType ->
                                    jsonObject.put(presentationFieldItem.title, presentationFieldItem.value)
                                is PresentationFieldItem.Header -> {
                                    // Do nothing
                                }
                                is PresentationFieldItem.IntType ->
                                    jsonObject.put(presentationFieldItem.title, presentationFieldItem.value)
                                is PresentationFieldItem.LongType ->
                                    jsonObject.put(presentationFieldItem.title, presentationFieldItem.value)
                                is PresentationFieldItem.StringType ->
                                    jsonObject.put(presentationFieldItem.title, presentationFieldItem.value)
                            }
                        }
                        jsonObject
                    }
            }.toMap()
            .let(::SaveOverrideFieldsInput)
}