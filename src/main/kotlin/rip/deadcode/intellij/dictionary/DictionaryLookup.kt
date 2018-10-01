package rip.deadcode.intellij.dictionary

import com.google.common.base.Splitter
import com.intellij.openapi.extensions.ExtensionPointName

object DictionaryLookup {

    private val splitter = Splitter.on(":").limit(1).trimResults()

    private val dictionaries = ExtensionPointName.create<Dictionary>("dictionary").extensions

    fun lookUp(input: String): String {

        val (prefix, word) = getPrefix(input)
        val dictionary = dictionaries.first { it.canHandle(prefix) }

        return dictionary.lookUp(word)
    }

    fun getPrefix(input: String): List<String> {
        return if (input.contains(":")) {
            splitter.splitToList(input)
        } else {
            listOf(TODO("Get default prefix"), input)
        }
    }
}
