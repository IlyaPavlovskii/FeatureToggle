package io.github.ilyapavlovskii.feature.toggle.sample

import android.app.Application
import io.github.ilyapavlovskii.feature.toggle.sample.toggles.FeatureToggleRegistrarHolder
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

internal class FeatureToggleSampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FeatureToggleRegistrarHolder.init(context = this)
    }
}