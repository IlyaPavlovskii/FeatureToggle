package by.bulba.feature.toggle

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

///**
// * Remote config deserializer interface
// */
//interface DeserializableFeatureToggleWrapper<out T : FeatureToggle> : FeatureToggle {
//
//    /**
//     * Converts string to corresponding [FeatureToggle] object
//     *
//     * @param jsonReader
//     * @param string json object
//     */
//    @Throws(SerializationException::class)
//    fun deserialize(jsonReader: Json, string: String): T
//}