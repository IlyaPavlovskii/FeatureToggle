package io.github.ilyapavlovskii.feature.toggle.debug.panel.model

import io.github.ilyapavlovskii.feature.toggle.FeatureToggleKey

interface FeatureToggleKeyOwner {
    val featureToggleKey: FeatureToggleKey
}