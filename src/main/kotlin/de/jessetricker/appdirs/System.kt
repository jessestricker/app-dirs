package de.jessetricker.appdirs

import org.jetbrains.annotations.TestOnly
import java.nio.file.InvalidPathException
import java.nio.file.Path
import kotlin.io.path.Path

internal interface System {
    /** Gets a system property, or `null` if it is not set. */
    fun getProperty(key: String): String?

    /** Gets an environment variable, or `null` if it is not set. */
    fun getEnvironmentVariable(name: String): String?
}

internal val System.osName: String get() = getStandardProperty("os.name")
internal val System.homeDir: Path get() = getEnvVarAsPath("HOME")

internal fun System.getStandardProperty(key: String): String {
    return getProperty(key) ?: throw InternalError("""The standard system property "$key" is not set.""")
}

internal fun System.getEnvVarAsPathOrNull(name: String): Path? {
    return getEnvironmentVariable(name)?.takeUnless { it.isEmpty() }?.let {
        try {
            Path(it)
        } catch (ignore: InvalidPathException) {
            throw InternalError("""The environment variable "$name" does not contain a valid path.""")
        }
    }
}

internal fun System.getEnvVarAsPath(name: String): Path {
    return getEnvVarAsPathOrNull(name)
        ?: throw InternalError("""The environment variable "$name" is not set or empty.""")
}

internal data object JreSystem : System {
    override fun getProperty(key: String): String? = java.lang.System.getProperty(key)
    override fun getEnvironmentVariable(name: String): String? = java.lang.System.getenv(name)
}

@set:TestOnly
internal var system: System = JreSystem
