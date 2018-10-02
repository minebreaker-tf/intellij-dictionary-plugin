package rip.deadcode.intellij.dictionary

import com.google.common.base.Splitter
import com.google.common.base.Strings.nullToEmpty
import com.intellij.openapi.extensions.ExtensionPointName

object DictionaryLookup {

    private val splitter = Splitter.on(":").limit(2).trimResults()
    private val dictionaries = ExtensionPointName.create<Dictionary>("rip.deadcode.intellij-dictionary-plugin.dictionary").extensions

    fun lookUp(input: String): String {

        val (prefix, word) = getPrefix(input)
        val dictionary = dictionaries.firstOrNull { it.canHandle(prefix) }

        return if (dictionary == null) {
            "Could not find the dictionary for the prefix."
        } else {
            dictionary.lookUp(word) ?: "No match found in the dictionary."
        }
    }

    fun getPrefix(input: String): List<String> {
        return if (input.contains(":")) {
            splitter.splitToList(input)
        } else {
            listOf(nullToEmpty(DictionaryConfig.getDefaultPrefix()), input)
        }
    }
}
