package de.jessestricker.appdirs

internal data class MockContext(
    private val systemProperties: Map<String, String> = emptyMap(),
    private val environmentVariables: Map<String, String> = emptyMap(),
) : Context() {
    override fun getSystemProperty(name: String): String? = systemProperties[name]
    override fun getEnvironmentVariable(name: String): String? = environmentVariables[name]
}
