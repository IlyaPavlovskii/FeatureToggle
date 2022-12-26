package by.bulba.feature.toggle.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import by.bulba.feature.toggle.get
import by.bulba.feature.toggle.sample.toggles.FeatureToggleRegistrarHolder
import by.bulba.feature.toggle.sample.toggles.SampleFeatureToggle
import by.bulba.feature.toggle.sample.ui.theme.FeatureToggleTheme

class MainActivity : ComponentActivity() {
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
                        text = "Hello FeatureToggle!",
                        color = Color(featureToggle.buttonColor),
                    )
                }
            }
        }
    }
}
