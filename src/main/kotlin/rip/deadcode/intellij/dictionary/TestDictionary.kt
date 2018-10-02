package rip.deadcode.intellij.dictionary

class TestDictionary : Dictionary{

    override fun lookUp(word: String): String = """
        <div>
            <h4>Sample for testing</h4>
            <p>Your input is: "${word}"
        </div>
    """.trimIndent()

    override fun canHandle(prefix: String): Boolean = prefix == "test"
}