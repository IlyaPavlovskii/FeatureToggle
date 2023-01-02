package by.bulba.feature.toggle.sample.toggles

import android.content.Context
import androidx.annotation.XmlRes
import by.bulba.feature.toggle.FeatureToggleContainer
import by.bulba.feature.toggle.FeatureToggleContainerHolder
import by.bulba.feature.toggle.FeatureToggleProvider
import by.bulba.feature.toggle.FeatureToggleRegistrar
import by.bulba.feature.toggle.SimpleFeatureToggleContainer
import by.bulba.feature.toggle.reader.ChainFeatureToggleReader
import by.bulba.feature.toggle.reader.FeatureToggleReaderHolder
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
    private var featureToggleRegistrar: FeatureToggleRegistrar? = null

    private val featureToggleContainer: FeatureToggleContainer = SimpleFeatureToggleContainer(
        featureToggles = setOf(
            MenuItemFeatureToggle(
                enabled = true,
                gridCount = 3,
                type = MenuItemFeatureToggle.PreviewType.GRID,
                addToCartAvailable = true,
            )
        )
    )

    fun init(context: Context) {
        val json = Json.Default
        val xmlConfigReader = XmlConfigReader(
            context = context,
            xmlRes = defaultXmlConfigResource,
        )
        val featureToggleReader = ChainFeatureToggleReader(
            featureReaders = arrayOf(
                XmlFileFeatureToggleReader(
                    json = json,
                    xmlConfigReader = XmlFileConfigReader(),
                    xmlConfigFileProvider = DefaultConfigFileProvider(
                        applicationContext = context,
                    ),
                ),
                ResourcesConfigReader(
                    json = json,
                    configReader = xmlConfigReader,
                ),
            )
        )
        FeatureToggleContainerHolder.init(featureToggleContainer)
        FeatureToggleReaderHolder.init(featureToggleReader)
        featureToggleRegistrar = FeatureToggleRegistrar(
            featureToggleContainer = featureToggleContainer,
            reader = featureToggleReader,
        ).setupFeatures()
    }

    fun featureToggleProvider(): FeatureToggleProvider =
        requireNotNull(featureToggleRegistrar)
}