package by.bulba.feature.toggle.reader

import android.util.Log
import androidx.annotation.XmlRes
import by.bulba.feature.toggle.FeatureToggle
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.time.Duration

/**
 * Firebase feature toggle configuration reader
 *
 * @param fetchTimeout the connection and read timeouts for fetch requests to the
 * Firebase Remote Config servers in seconds. A fetch call will fail if it takes longer than
 * the specified timeout to connect to or read from the Remote Config servers.
 * @param minimumFetchInterval the minimum interval between successive fetch calls.
 * Fetches less than duration seconds after the last fetch from the Firebase Remote
 * Config server would use values returned during the last fetch.
 * @param json default Json instance to work with serialization/deserialization instances
 * @param defaultConfigRes default configuration fallback. Uses in cases when key hasn't overridden
 * remotely.
 * */
class FirebaseFeatureToggleReader(
    fetchTimeout: Duration,
    minimumFetchInterval: Duration,
    private val json: Json,
    @XmlRes defaultConfigRes: Int
) : FeatureToggleReader {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    init {
        val remoteConfigSettings = remoteConfigSettings {
            fetchTimeoutInSeconds = fetchTimeout.inWholeSeconds
            minimumFetchIntervalInSeconds = minimumFetchInterval.inWholeSeconds
        }
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings)
        remoteConfig.setDefaultsAsync(defaultConfigRes)
        updateConfig()
    }

    @OptIn(InternalSerializationApi::class)
    override fun readFeature(feature: FeatureToggle): FeatureToggle? {
        val jsonString = remoteConfig.getString(feature.toggleKey.key)
        if (jsonString.isEmpty()) {
            Log.d(TAG, "Remote config for '${feature.toggleKey.key}' key was empty.")
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

    private fun updateConfig() {
        // Note: using strategy 3 from https://firebase.google.com/docs/remote-config/loading?authuser=0
        remoteConfig.activate()
            .addOnCompleteListener {
                remoteConfig.fetch()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "New config was fetched")
                        } else {
                            Log.w(TAG, "Could not fetch new config")
                        }
                    }
            }
    }

    private companion object {

        private const val TAG = "FirebaseConfigReader"
    }
}