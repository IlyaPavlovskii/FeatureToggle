package by.bulba.feature.toggle.sample

import android.app.Application
import by.bulba.feature.toggle.sample.toggles.FeatureToggleRegistrarHolder

internal class FeatureToggleSampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FeatureToggleRegistrarHolder.init(context = this)
    }
}