package by.bulba.feature.toggle.sample.toggles

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.XmlRes
import by.bulba.feature.toggle.FeatureToggleProvider
import by.bulba.feature.toggle.FeatureToggleRegistrar
import by.bulba.feature.toggle.reader.ChainFeatureToggleReader
import by.bulba.feature.toggle.reader.ResourcesConfigReader
import by.bulba.feature.toggle.reader.XmlConfigReader
import by.bulba.feature.toggle.reader.XmlFileFeatureToggleReader
import by.bulba.feature.toggle.sample.R
import by.bulba.feature.toggle.util.DefaultConfigFileProvider
import by.bulba.feature.toggle.util.XmlFileConfigReader
import kotlinx.serialization.json.Json

object FeatureToggleRegistrarHolder {
    @XmlRes
    private val defaultXmlConfigResource: Int = R.xml.default_feature_toggle_config
    private var sampleFeatureToggleRegistrar: FeatureToggleRegistrar? = null

    fun init(context: Context) {
        val json = Json.Default
        val xmlConfigReader = XmlConfigReader(
            context = context,
            xmlRes = defaultXmlConfigResource,
        )
        sampleFeatureToggleRegistrar = FeatureToggleRegistrar(
            registeredFeatures = setOf(
                SampleFeatureToggle(
                    buttonColor = 0xffff0000,
                    type = SampleFeatureToggle.Type.INACTIVE,
                )
            ),
            reader = ChainFeatureToggleReader(
                featureReaders = arrayOf(
                    ResourcesConfigReader(
                        json = json,
                        configReader = xmlConfigReader,
                    ),
                    XmlFileFeatureToggleReader(
                        json = json,
                        xmlConfigReader = XmlFileConfigReader(),
                        xmlConfigFileProvider = DefaultConfigFileProvider(
                            applicationContext = context,
                        ),
                    )
                )
            ),
        ).apply {
            setupFeatures()
        }
    }

    fun featureToggleProvider(): FeatureToggleProvider =
        requireNotNull(sampleFeatureToggleRegistrar)
}