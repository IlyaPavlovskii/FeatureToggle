package by.bulba.feature.toggle.reader

import android.util.Log
import by.bulba.feature.toggle.FeatureToggle
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.lang.ref.WeakReference

/**
 * Processes configuration from default local XML config.
 * */
class ResourcesConfigReader(
    private val json: Json,
    private val configReader: ConfigReader,
) : FeatureToggleReader {

    private var configMap: WeakReference<Map<String, String>?> = WeakReference(null)

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

    private fun fetchConfig(): Map<String, String> {
        var config = configMap.get()
        if (config == null) {
            config = configReader.readConfig()
            configMap = WeakReference(config)
        }
        return config
    }

    private class LocalToggleKeyNotExistError(toggleKey: String) :
        Throwable(message = "Resources config doesn't have key '$toggleKey'")

    private companion object {
        private const val TAG = "ResourcesConfigReader"
    }
}
