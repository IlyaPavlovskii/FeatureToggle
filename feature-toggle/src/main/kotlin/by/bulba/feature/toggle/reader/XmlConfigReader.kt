package by.bulba.feature.toggle.reader

import android.content.Context
import android.content.res.XmlResourceParser
import androidx.annotation.XmlRes

/**
 * Remote config xml parser
 */
class XmlConfigReader(
    private val context: Context,
    @XmlRes private val xmlRes: Int,
) : ConfigReader {

    /**
     * Reads config to xml key, value [Map]
     */
    override fun readConfig(): Map<String, String> {
        val config: MutableMap<String, String> = mutableMapOf()
        val xmlResourceParser = context.resources.getXml(xmlRes)
        var isInKeyTag = false
        var isInValueTag = false
        var currentKey: String? = null
        while (xmlResourceParser.eventType != XmlResourceParser.END_DOCUMENT) {
            when (xmlResourceParser.eventType) {
                XmlResourceParser.START_TAG -> {
                    val tagName = xmlResourceParser.name
                    if (tagName == KEY) {
                        isInKeyTag = true
                    } else if (tagName == VALUE) {
                        isInValueTag = true
                    }
                }
                XmlResourceParser.END_TAG -> {
                    val tagName = xmlResourceParser.name
                    if (tagName == KEY) {
                        isInKeyTag = false
                    } else if (tagName == VALUE) {
                        isInValueTag = false
                    }
                }
                XmlResourceParser.TEXT ->
                    if (isInKeyTag) {
                        currentKey = xmlResourceParser.text
                    } else if (isInValueTag) {
                        config[requireNotNull(currentKey)] = xmlResourceParser.text
                        currentKey = null
                    }
            }
            xmlResourceParser.next()
        }
        return config
    }

    private companion object {

        private const val KEY = "key"
        private const val VALUE = "value"
    }
}
