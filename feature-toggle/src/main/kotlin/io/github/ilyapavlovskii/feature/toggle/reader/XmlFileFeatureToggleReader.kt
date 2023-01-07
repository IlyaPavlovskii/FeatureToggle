package io.github.ilyapavlovskii.feature.toggle.reader

import android.util.Log
import io.github.ilyapavlovskii.feature.toggle.FeatureToggle
import io.github.ilyapavlovskii.feature.toggle.util.XmlConfigFileProvider
import io.github.ilyapavlovskii.feature.toggle.util.XmlFileConfigReader
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class)
class XmlFileFeatureToggleReader(
    private val json: Json,
    private val xmlConfigReader: XmlFileConfigReader,
    private val xmlConfigFileProvider: XmlConfigFileProvider,
) : FeatureToggleReader {

    override fun readFeature(feature: FeatureToggle): FeatureToggle? {
        val config = fetchConfig()
        val jsonString = config[feature.toggleKey.key]
        if (jsonString.isNullOrEmpty()) {
            return null
        }
        try {
            return json.decodeFromString(feature::class.serializer(), jsonString)
        } catch (ex: SerializationException) {
            Log.e(
                TAG,
                "For key '${feature.toggleKey.key}', Could not parse json string '$jsonString'",
                ex
            )
        }
        return null
    }

    private fun fetchConfig(): Map<String, String> =
        xmlConfigReader.readConfig(xmlConfigFileProvider.getFeatureToggleFile())

    private companion object {
        private const val TAG = "XmlFileFeatureToggleReader"
    }
}
