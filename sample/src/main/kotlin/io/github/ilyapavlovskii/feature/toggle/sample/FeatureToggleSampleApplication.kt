package io.github.ilyapavlovskii.feature.toggle.sample

import android.app.Application
import io.github.ilyapavlovskii.feature.toggle.toggles.FeatureToggleRegistrarHolder
import com.google.firebase.FirebaseApp

internal class FeatureToggleSampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FeatureToggleRegistrarHolder.init(context = this)
    }
}