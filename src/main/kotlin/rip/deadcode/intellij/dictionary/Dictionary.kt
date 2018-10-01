package rip.deadcode.intellij.dictionary

interface Dictionary {

    fun lookUp(word: String): String

    fun canHandle(prefix: String): Boolean
}
