package by.bulba.feature.toggle.util

import android.util.Xml
import java.io.File
import java.io.StringWriter

class XmlConfigWriter {

    fun write(file: File?, featureToggles: Map<String, String>): Boolean {
        val writer = StringWriter()
        Xml.newSerializer().apply {
            setOutput(writer)
            startDocument("UTF-8", true)
            startTag(null, DEFAULTS_MAP_TAG)
            startTag(null, ENTRY_TAG)
            featureToggles.forEach { (key, value) ->
                startTag(null, KEY_TAG)
                text(key)
                endTag(null, KEY_TAG)
                startTag(null, VALUE_TAG)
                text(value)
                endTag(null, VALUE_TAG)
            }
            endTag(null, ENTRY_TAG)
            endTag(null, DEFAULTS_MAP_TAG)
            endDocument()
            flush()
        }
        return if (file == null) {
            false
        } else {
            file.writeText(writer.toString())
            true
        }
    }

    companion object {
        const val DEFAULTS_MAP_TAG = "defaultsMap"
        const val ENTRY_TAG = "entry"
        const val KEY_TAG = "key"
        const val VALUE_TAG = "value"
    }
}
