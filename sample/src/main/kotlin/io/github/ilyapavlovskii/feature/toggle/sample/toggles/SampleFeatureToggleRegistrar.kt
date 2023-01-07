package io.github.ilyapavlovskii.feature.toggle.sample.toggles

import android.content.Context
import androidx.annotation.XmlRes
import io.github.ilyapavlovskii.feature.toggle.FeatureToggleContainer
import io.github.ilyapavlovskii.feature.toggle.FeatureToggleContainerHolder
import io.github.ilyapavlovskii.feature.toggle.FeatureToggleProvider
import io.github.ilyapavlovskii.feature.toggle.FeatureToggleRegistrar
import io.github.ilyapavlovskii.feature.toggle.SimpleFeatureToggleContainer
import io.github.ilyapavlovskii.feature.toggle.reader.ChainFeatureToggleReader
import io.github.ilyapavlovskii.feature.toggle.reader.ConfigReader
import io.github.ilyapavlovskii.feature.toggle.reader.FeatureToggleReaderHolder
import io.github.ilyapavlovskii.feature.toggle.reader.FirebaseFeatureToggleReader
import io.github.ilyapavlovskii.feature.toggle.reader.ResourcesFeatureToggleReader
import io.github.ilyapavlovskii.feature.toggle.reader.XmlConfigReader
import io.github.ilyapavlovskii.feature.toggle.reader.XmlFileFeatureToggleReader
import io.github.ilyapavlovskii.feature.toggle.sample.R
import io.github.ilyapavlovskii.feature.toggle.util.DefaultConfigFileProvider
import io.github.ilyapavlovskii.feature.toggle.util.XmlFileConfigReader
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toDuration

object FeatureToggleRegistrarHolder {
    private val json: Json = Json.Default
    @XmlRes
    private val defaultXmlConfigResource: Int = R.xml.default_feature_toggle_config
    private var featureToggleRegistrar: FeatureToggleRegistrar? = null

    private val featureToggleContainer: FeatureToggleContainer = SimpleFeatureToggleContainer(
        featureToggles = setOf(
            SampleTitleFeatureToggle(
                enabled = true,
                title = "Sample FeatureToggle",
            ),
            RestaurantInfoFeatureToggle(
                mapVisible = true,
                deliveryCostsVisible = true,
                ratingVisible = true,
            ),
            MenuItemFeatureToggle(
                enabled = true,
                gridCount = 3,
                type = MenuItemFeatureToggle.PreviewType.HORIZONTAL_LIST,
                addToCartAvailable = true,
            )
        )
    )

    fun init(context: Context) {
        val xmlConfigReader = createXmlConfigReader(context)
        val featureToggleReader = ChainFeatureToggleReader(
            featureReaders = arrayOf(
                XmlFileFeatureToggleReader(
                    json = json,
                    xmlConfigReader = XmlFileConfigReader(),
                    xmlConfigFileProvider = DefaultConfigFileProvider(
                        applicationContext = context,
                    ),
                ),
                FirebaseFeatureToggleReader(
                    fetchTimeout = 60.seconds,
                    minimumFetchInterval = 12.hours,
                    json = json,
                    defaultConfigRes = defaultXmlConfigResource,
                ),
                ResourcesFeatureToggleReader(
                    json = json,
                    configReader = xmlConfigReader,
                ),
            )
        )
        FeatureToggleContainerHolder.init(featureToggleContainer)
        FeatureToggleReaderHolder.init(featureToggleReader)
        featureToggleRegistrar = FeatureToggleRegistrar().setupFeatures()
    }

    private fun createXmlConfigReader(context: Context): ConfigReader = XmlConfigReader(
        context = context,
        xmlRes = defaultXmlConfigResource,
    )

    fun featureToggleProvider(): FeatureToggleProvider =
        requireNotNull(featureToggleRegistrar)
}