package by.bulba.feature.toggle.debug.panel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import by.bulba.feature.toggle.FeatureToggle
import by.bulba.feature.toggle.FeatureToggleContainer
import by.bulba.feature.toggle.FeatureToggleContainerHolder
import by.bulba.feature.toggle.debug.panel.domain.SaveOverrideFieldsResult
import by.bulba.feature.toggle.debug.panel.domain.SaveOverrideFieldsUseCase
import by.bulba.feature.toggle.debug.panel.model.DebugPanelViewState
import by.bulba.feature.toggle.debug.panel.model.DomainFieldItemMapper
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItem
import by.bulba.feature.toggle.debug.panel.model.PresentationFieldItemMapper
import by.bulba.feature.toggle.debug.panel.utils.mutate
import by.bulba.feature.toggle.reader.FeatureToggleReader
import by.bulba.feature.toggle.reader.FeatureToggleReaderHolder
import by.bulba.feature.toggle.util.DefaultConfigFileProvider
import by.bulba.feature.toggle.util.XmlConfigFileProvider
import by.bulba.feature.toggle.util.XmlConfigWriter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

internal class DebugPanelViewModel(
    private val fieldItemMapper: PresentationFieldItemMapper = PresentationFieldItemMapper.create(),
    private val featureToggleContainer: FeatureToggleContainer = FeatureToggleContainerHolder.getContainer(),
    private val reader: FeatureToggleReader = FeatureToggleReaderHolder.getFeatureToggleReader(),
    private val xmlConfigWriter: XmlConfigWriter = XmlConfigWriter(),
    private val xmlConfigFileProvider: XmlConfigFileProvider,
    private val domainFieldItemMapper: DomainFieldItemMapper =
        DomainFieldItemMapper.create(featureToggleContainer),
    private val saveOverrideFieldsUseCase: SaveOverrideFieldsUseCase = SaveOverrideFieldsUseCase
        .create(
            xmlConfigFileProvider = xmlConfigFileProvider,
            xmlConfigWriter = xmlConfigWriter,
        )
) : ViewModel() {
    private val initialItems: MutableList<PresentationFieldItem> = mutableListOf()
    private val _viewState = MutableStateFlow(DebugPanelViewState.DEFAULT)
    val viewState: StateFlow<DebugPanelViewState> = _viewState

    init {
        initValues()
    }

    fun resetToDefault() {
        xmlConfigFileProvider.getFeatureToggleFile()?.delete()
        initValues()
        _viewState.mutate {
            this.copy(
                items = initialItems,
                resetToDefaultAvailable = false,
                saveAvailable = false,
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
    }

    fun save() {
        viewModelScope.launch {
            val content = _viewState.value
            val input = domainFieldItemMapper.convert(content.items)
            when(saveOverrideFieldsUseCase.execute(input)) {
                SaveOverrideFieldsResult.FileSaveError -> {
                    _viewState.mutate {
                        this.copy(
                            dialog = DebugPanelViewState.Dialog(
                                title = R.string.debug_panel__error,
                                message = R.string.debug_panel__failed_to_save_file,
                                button = R.string.debug_panel__cancel,
                            )
                        )
                    }
                }
                SaveOverrideFieldsResult.Success -> {
                    _viewState.mutate {
                        this.copy(
                            dialog = DebugPanelViewState.Dialog(
                                title = R.string.debug_panel__success,
                                message = R.string.debug_panel__config_successfully_saved,
                                button = R.string.debug_panel__ok,
                            )
                        )
                    }
                }
            }
        }
    }

    private fun initValues() {
        viewModelScope.launch {
            val items = featureToggleContainer.getFeatureToggles()
                .mapNotNull(reader::readFeature)
                .flatMap(fieldItemMapper::convert)
            initialItems.clear()
            initialItems.addAll(items)
            _viewState.value = DebugPanelViewState(
                resetToDefaultAvailable = false,
                saveAvailable = false,
                items = items,
            )
        }
    }

    fun dismissDialog() {
        _viewState.mutate { this.copy(dialog = null) }
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


