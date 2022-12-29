package by.bulba.feature.toggle.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import by.bulba.feature.toggle.design.system.theme.FeatureToggleTheme
import by.bulba.feature.toggle.get
import by.bulba.feature.toggle.sample.toggles.FeatureToggleRegistrarHolder
import by.bulba.feature.toggle.sample.toggles.SampleFeatureToggle

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalUnitApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val featureToggle = FeatureToggleRegistrarHolder.featureToggleProvider()
            .get<SampleFeatureToggle>()
        setContent {
            FeatureToggleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = "Hello FeatureToggle! Text: ${featureToggle.text}"
                            .substring(0, featureToggle.textLength),
                        color = Color(featureToggle.buttonColor),
                        fontSize = TextUnit(value = featureToggle.textSize, type = TextUnitType.Sp),
                    )
                }
            }
        }
    }
}
