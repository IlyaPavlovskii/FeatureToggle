package by.bulba.feature.toggle.util

import android.content.res.XmlResourceParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.File
import java.io.FileInputStream

/**
 * Remote config xml parser
 */
class XmlFileConfigReader {

    /**
     * Reads config to xml key, value [Map]
     */
    fun readConfig(file: File?): Map<String, String> {
        val config: MutableMap<String, String> = mutableMapOf()
        val factory = XmlPullParserFactory.newInstance()
        val xmlPullParser = factory.newPullParser()
        file ?: return emptyMap()
        if (!file.exists()) return emptyMap()
        val fileInputStream = FileInputStream(file)
        xmlPullParser.setInput(fileInputStream, null)
        var isInKeyTag = false
        var isInValueTag = false
        var currentKey: String? = null
        while (xmlPullParser.eventType != XmlResourceParser.END_DOCUMENT) {
            when (xmlPullParser.eventType) {
                XmlResourceParser.START_TAG -> {
                    val tagName = xmlPullParser.name
                    if (tagName == KEY) {
                        isInKeyTag = true
                    } else if (tagName == VALUE) {
                        isInValueTag = true
                    }
                }
                XmlResourceParser.END_TAG -> {
                    val tagName = xmlPullParser.name
                    if (tagName == KEY) {
                        isInKeyTag = false
                    } else if (tagName == VALUE) {
                        isInValueTag = false
                    }
                }
                XmlResourceParser.TEXT ->
                    if (isInKeyTag) {
                        currentKey = xmlPullParser.text
                    } else if (isInValueTag) {
                        config[requireNotNull(currentKey)] = xmlPullParser.text
                        currentKey = null
                    }
            }
            xmlPullParser.next()
        }
        return config
    }

    private companion object {

        private const val KEY = "key"
        private const val VALUE = "value"
    }
}
