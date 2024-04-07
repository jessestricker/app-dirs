package de.jessetricker.appdirs.util

import de.jessetricker.appdirs.System
import de.jessetricker.appdirs.system

class FakeSystem(
    private val properties: Map<String, String> = emptyMap(),
    private val environmentVariables: Map<String, String> = emptyMap(),
) : System {
    override fun getProperty(key: String): String? = properties[key]
    override fun getEnvironmentVariable(name: String): String? = environmentVariables[name]
}

fun withFakeSystem(
    properties: Map<String, String> = emptyMap(),
    environmentVariables: Map<String, String> = emptyMap(),
    block: () -> Unit,
) {
    val previousSystem = system
    system = FakeSystem(properties, environmentVariables)
    try {
        block()
    } finally {
        system = previousSystem
    }
}
