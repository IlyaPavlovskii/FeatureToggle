package io.github.ilyapavlovskii.feature.toggle.debug.panel.domain

import io.github.ilyapavlovskii.feature.toggle.FeatureToggleKey
import io.github.ilyapavlovskii.feature.toggle.util.XmlConfigFileProvider
import io.github.ilyapavlovskii.feature.toggle.debug.panel.util.XmlConfigWriter
import org.json.JSONObject

internal interface SaveOverrideFieldsUseCase {
    suspend fun execute(input: SaveOverrideFieldsInput): SaveOverrideFieldsResult

    companion object {
        fun create(
            xmlConfigFileProvider: XmlConfigFileProvider,
            xmlConfigWriter: XmlConfigWriter,
        ): SaveOverrideFieldsUseCase = DefaultSaveOverrideFieldsUseCase(
            xmlConfigFileProvider = xmlConfigFileProvider,
            xmlConfigWriter = xmlConfigWriter,
        )
    }
}

internal data class SaveOverrideFieldsInput(
    val updateItems: Map<FeatureToggleKey, JSONObject>
)

internal sealed class SaveOverrideFieldsResult {
    object Success : SaveOverrideFieldsResult()

    object FileSaveError : SaveOverrideFieldsResult()
}

private class DefaultSaveOverrideFieldsUseCase(
    private val xmlConfigFileProvider: XmlConfigFileProvider,
    private val xmlConfigWriter: XmlConfigWriter,
) : SaveOverrideFieldsUseCase {
    override suspend fun execute(input: SaveOverrideFieldsInput): SaveOverrideFieldsResult {
        val result = xmlConfigWriter.write(
            file = xmlConfigFileProvider.getFeatureToggleFile(),
            featureToggles = input.updateItems.map { (featureToggleKey, jsonObject) ->
                featureToggleKey.key to jsonObject.toString()
            }.toMap()
        )
        return if (result) {
            SaveOverrideFieldsResult.Success
        } else {
            SaveOverrideFieldsResult.FileSaveError
        }
    }

}