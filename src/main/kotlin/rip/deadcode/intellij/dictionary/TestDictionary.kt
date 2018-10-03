package rip.deadcode.intellij.dictionary

class TestDictionary : Dictionary{

    override fun lookUp(word: String): String = """
        <div>
            <h2>Sample for testing</h2>
            <p>Your input is: "${word}"
        </div>
    """.trimIndent()

    override fun canHandle(prefix: String): Boolean = prefix == "test"

    override fun getDisplayName(): String = "Test Dictionary"
}
