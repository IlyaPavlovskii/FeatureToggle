package by.bulba.feature.toggle.sample.toggles

import android.content.Context
import androidx.annotation.XmlRes
import by.bulba.feature.toggle.FeatureToggleContainer
import by.bulba.feature.toggle.FeatureToggleContainerHolder
import by.bulba.feature.toggle.FeatureToggleProvider
import by.bulba.feature.toggle.FeatureToggleRegistrar
import by.bulba.feature.toggle.SimpleFeatureToggleContainer
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

    private val featureToggleContainer: FeatureToggleContainer = SimpleFeatureToggleContainer(
        featureToggles = setOf(
            SampleFeatureToggle(
                buttonColor = 0xffff0000,
                text = "Sample text",
                textSize = 16f,
                textLength = 50,
                popUpEnabled = true,
                type = SampleFeatureToggle.Type.INACTIVE,
            )
        )
    )

    fun init(context: Context) {
        val json = Json.Default
        val xmlConfigReader = XmlConfigReader(
            context = context,
            xmlRes = defaultXmlConfigResource,
        )
        FeatureToggleContainerHolder.init(featureToggleContainer)
        sampleFeatureToggleRegistrar = FeatureToggleRegistrar(
            featureToggleContainer = featureToggleContainer,
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
        ).setupFeatures()
    }

    fun featureToggleProvider(): FeatureToggleProvider =
        requireNotNull(sampleFeatureToggleRegistrar)
}