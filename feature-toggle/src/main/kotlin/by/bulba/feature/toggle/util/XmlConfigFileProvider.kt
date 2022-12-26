package by.bulba.feature.toggle.util

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Internal file XML configuration [File] provider
 */
interface XmlConfigFileProvider {

    fun getFeatureToggleFile(): File?

    /**
     * Get temporary file that will be uses for debug panel, temporary storage before apply changes
     * */
    fun getTmpDebugChangesToggleFile(): File?
}

class DefaultConfigFileProvider(
    private val applicationContext: Context,
    private val featureToggleFileName: String = FEATURE_TOGGLE_FILE_NAME,
    private val tmpFeatureToggleFileName: String = TEMPORARY_FEATURE_TOGGLE_FILE_NAME,
) : XmlConfigFileProvider {

    override fun getFeatureToggleFile(): File? = applicationContext
        .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        ?.resolve(featureToggleFileName)

    override fun getTmpDebugChangesToggleFile(): File? = applicationContext
        .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        ?.resolve(tmpFeatureToggleFileName)

    companion object {
        private const val FEATURE_TOGGLE_FILE_NAME = "internal_feature_toggle.xml"
        private const val TEMPORARY_FEATURE_TOGGLE_FILE_NAME = "tmp_internal_file_name.xml"
    }
}