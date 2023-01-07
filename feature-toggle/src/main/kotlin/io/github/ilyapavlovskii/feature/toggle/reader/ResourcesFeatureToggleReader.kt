package io.github.ilyapavlovskii.feature.toggle.reader

import android.util.Log
import io.github.ilyapavlovskii.feature.toggle.FeatureToggle
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * Processes configuration from default local XML config.
 * */
class ResourcesFeatureToggleReader(
    private val json: Json,
    private val configReader: ConfigReader,
) : FeatureToggleReader {

    @OptIn(InternalSerializationApi::class)
    override fun readFeature(feature: FeatureToggle): FeatureToggle? {
        val config = fetchConfig()
        val jsonString = config[feature.toggleKey.key]
        if (jsonString.isNullOrEmpty()) {
            Log.e(TAG, null, LocalToggleKeyNotExistError(feature.toggleKey.key))
            return null
        }
        try {
            val deserializeValue = json.decodeFromString(feature::class.serializer(), jsonString)
            Log.d(TAG, "Deserialize value for key '${feature.toggleKey.key}' is $deserializeValue")
            return deserializeValue
        } catch (ex: SerializationException) {
            Log.e(TAG, "For key '${feature.toggleKey.key}', Could not parse json string '$jsonString'", ex)
        }
        return null
    }

    private fun fetchConfig(): Map<String, String> = configReader.readConfig()

    private class LocalToggleKeyNotExistError(toggleKey: String) :
        Throwable(message = "Resources config doesn't have key '$toggleKey'")

    private companion object {
        private const val TAG = "ResourcesConfigReader"
    }
}
