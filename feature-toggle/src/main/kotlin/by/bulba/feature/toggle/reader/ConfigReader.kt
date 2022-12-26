package by.bulba.feature.toggle.reader

/**
 * Configuration reader abstraction
 * */
interface ConfigReader {
    /**
     * Reads config to xml key, value [Map]
     */
    fun readConfig(): Map<String, String>
}