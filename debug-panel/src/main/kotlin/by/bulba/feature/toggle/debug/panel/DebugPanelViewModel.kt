package by.bulba.feature.toggle.debug.panel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import by.bulba.feature.toggle.FeatureToggle
import by.bulba.feature.toggle.FeatureToggleContainer
import by.bulba.feature.toggle.FeatureToggleContainerHolder
import by.bulba.feature.toggle.debug.panel.model.DebugPanelViewState
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItem
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItemMapper
import by.bulba.feature.toggle.debug.panel.utils.mutate
import by.bulba.feature.toggle.reader.FeatureToggleReader
import by.bulba.feature.toggle.reader.FeatureToggleReaderHolder
import by.bulba.feature.toggle.util.DefaultConfigFileProvider
import by.bulba.feature.toggle.util.XmlConfigFileProvider
import by.bulba.feature.toggle.util.XmlConfigWriter
import by.bulba.feature.toggle.util.XmlFileConfigReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

internal class DebugPanelViewModel(
    private val fieldItemMapper: PresentationFieldItemMapper = PresentationFieldItemMapper.create(),
    private val featureToggleContainer: FeatureToggleContainer = FeatureToggleContainerHolder.getContainer(),
    private val xmlFileConfigReader: XmlFileConfigReader = XmlFileConfigReader(),
    private val xmlConfigWriter: XmlConfigWriter = XmlConfigWriter(),
    private val reader: FeatureToggleReader = FeatureToggleReaderHolder.getFeatureToggleReader(),
    private val json: Json = Json.Default,
    private val xmlConfigFileProvider: XmlConfigFileProvider,
) : ViewModel() {
    private val initialItems: MutableList<PresentationFieldItem> = mutableListOf()
    private val _viewState = MutableStateFlow<DebugPanelViewState>(DebugPanelViewState.Loading)
    val viewState: StateFlow<DebugPanelViewState> = _viewState

    init {
        viewModelScope.launch {
            val items = featureToggleContainer.getFeatureToggles()
                .flatMap(fieldItemMapper::convert)
            initialItems.addAll(items)
            _viewState.value = DebugPanelViewState.Content(
                resetToDefaultAvailable = false,
                saveAvailable = false,
                items = items,
            )
        }
    }

    fun updateBooleanValue(oldItem: PresentationFieldItem.BooleanType, newValue: Boolean) =
        updateValue(oldItem = oldItem, newItem = oldItem.copy(enabled = newValue))

    fun updateFloatValue(oldItem: PresentationFieldItem.FloatType, newValue: Float) =
        updateValue(oldItem = oldItem, newItem = oldItem.copy(value = newValue))

    fun updateLongValue(oldItem: PresentationFieldItem.LongType, newValue: Long) =
        updateValue(oldItem = oldItem, newItem = oldItem.copy(value = newValue))

    fun updateIntValue(oldItem: PresentationFieldItem.IntType, newValue: Int) =
        updateValue(oldItem = oldItem, newItem = oldItem.copy(value = newValue))

    fun updateStringValue(oldItem: PresentationFieldItem.StringType, newValue: String) =
        updateValue(oldItem = oldItem, newItem = oldItem.copy(value = newValue))

    fun selectEnumValue(item: PresentationFieldItem.EnumType, newValue: String) =
        updateValue(oldItem = item, newItem = item.copy(selectedValue = newValue))

    private fun updateValue(oldItem: PresentationFieldItem, newItem: PresentationFieldItem) {
        _viewState.mutate {
            when (this) {
                is DebugPanelViewState.Content -> {
                    val newItems = this.items.map { fieldItem ->
                        if (fieldItem == oldItem) {
                            newItem
                        } else {
                            fieldItem
                        }
                    }
                    val saveAvailable = newItems != initialItems
                    this.copy(
                        items = newItems,
                        resetToDefaultAvailable = saveAvailable,
                        saveAvailable = saveAvailable,
                    )
                }

                DebugPanelViewState.Loading -> error("Unexpected Loading view state")
            }
        }
    }

    fun resetToDefault() {
        _viewState.mutate {
            when(this) {
                is DebugPanelViewState.Content -> this.copy(
                    items = initialItems,
                    resetToDefaultAvailable = false,
                    saveAvailable = false,
                )
                DebugPanelViewState.Loading -> error("Unexpected Loading view state")
            }
        }
    }

    fun save() {
//        viewModelScope.launch {
//
//            _viewState.value = DebugPanelViewState.Loading
//            val featuresMap = getFeaturesMap()
//            val newXmlMap = featuresMap.associate {featureToggle ->
//                //featureToggle.toXmlJsonPair(json, requireNotNull(_data.value))
//                featureToggle.toXmlJsonPair(json, emptyList())
//            }
//            val defaultMap = xmlFileConfigReader.readConfig(
//                file = xmlConfigFileProvider.getTmpDebugChangesToggleFile()
//            )
//            val updatedMap = newXmlMap.filter { (key, value) -> defaultMap[key] != value }
//            //logDebug(TAG, "Updating:\n${updatedMap.map { it.toPair() }.joinToString("\n")}")
//            val isWriteSuccess = xmlConfigWriter.write(
//                file = xmlConfigFileProvider.getFeatureToggleFile(),
//                featureToggles = updatedMap
//            )
//        }
        val items = featureToggleContainer.getFeatureToggles().flatMap(fieldItemMapper::convert)
        _viewState.value = DebugPanelViewState.Content(
            resetToDefaultAvailable = true,
            saveAvailable = true,
            items = items,
        )
    }

    private suspend fun updateToggles() {
//        val overriddenKeys = xmlFileConfigReader
//            .readConfig(file = xmlConfigFileProvider.getFeatureToggleFile())
//            .keys
//        if (overriddenKeys.isEmpty()) {
//            val noLocalChangesState = getFeaturesMap().associate { it.toXmlJsonPair(json, emptyList()) }
//            xmlConfigWriter.write(
//                file = xmlConfigFileProvider.getTmpDebugChangesToggleFile(),
//                featureToggles = noLocalChangesState
//            )
//        }
//        delay(FILE_MANIPULATION_DELAY)
//        _data.value = getFeaturesMap().flatMap {
//            val isOverridden = overriddenKeys.contains(it.toggleKey.key)
//            it.toUiModel(json, isOverridden)
//        }
    }

    private fun getFeaturesMap(): List<FeatureToggle> {
        val featuresMap: MutableMap<KClass<out FeatureToggle>, FeatureToggle> =
            featureToggleContainer
                .getFeatureToggles()
                .associateBy { featureToggle -> featureToggle::class }
                .toMutableMap()
        featuresMap.forEach { entry ->
            val key = entry.key
            val feature = entry.value
            reader.readFeature(feature)
                ?.also { featureToggle -> featuresMap[key] = featureToggle }
        }
        return featuresMap.values.toList()
    }

    companion object {
        fun factory(applicationContext: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DebugPanelViewModel(
                    xmlConfigFileProvider = DefaultConfigFileProvider(
                        applicationContext = applicationContext
                    ),
                )
            }
        }
    }
}


