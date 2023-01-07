package io.github.ilyapavlovskii.feature.toggle.util

import android.content.Context
import java.io.File

/**
 * Internal file XML configuration [File] provider
 */
interface XmlConfigFileProvider {

    fun getFeatureToggleFile(): File?
}

class DefaultConfigFileProvider(
    private val applicationContext: Context,
    private val featureToggleFileName: String = FEATURE_TOGGLE_FILE_NAME,
) : XmlConfigFileProvider {

    override fun getFeatureToggleFile(): File? = applicationContext
        .filesDir?.resolve(featureToggleFileName)

    companion object {
        private const val FEATURE_TOGGLE_FILE_NAME = "internal_feature_toggle.xml"
    }
}