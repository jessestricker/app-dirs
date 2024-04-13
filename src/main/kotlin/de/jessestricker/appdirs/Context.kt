package de.jessestricker.appdirs

import java.nio.file.InvalidPathException
import java.nio.file.Path
import kotlin.io.path.Path

internal class IllegalSystemConfigurationError(message: String, cause: Throwable? = null) : Error(message, cause)

internal abstract class Context {
    abstract fun getSystemProperty(name: String): String?
    abstract fun getEnvironmentVariable(name: String): String?

    fun getStandardSystemProperty(name: String): String =
        (getSystemProperty(name)
            ?: throw IllegalSystemConfigurationError("""The standard system property "$name" is not set."""))
            .ifEmpty {
                throw IllegalSystemConfigurationError(
                    """The standard system property "$name" is empty."""
                )
            }

    fun getStandardSystemPropertyPath(name: String): Path =
        getStandardSystemProperty(name).toPath().getOrElse {
            throw IllegalSystemConfigurationError(
                """The standard system property "$name" does not contain a valid path.""", it
            )
        }

    fun getEnvironmentVariablePath(name: String): Path? =
        getEnvironmentVariable(name)?.takeUnless { it.isEmpty() }?.toPath()?.getOrElse {
            throw IllegalSystemConfigurationError(
                """The environment variable "$name" does not contain a valid path.""", it
            )
        }

    private fun getStandardEnvironmentVariable(name: String): String =
        (getEnvironmentVariable(name)
            ?: throw IllegalSystemConfigurationError("""The standard environment variable "$name" is not set."""))
            .ifEmpty {
                throw IllegalSystemConfigurationError(
                    """The standard environment variable "$name" is empty."""
                )
            }

    fun getStandardEnvironmentVariablePath(name: String): Path =
        getStandardEnvironmentVariable(name).toPath().getOrElse {
            throw IllegalSystemConfigurationError(
                """The standard environment variable "$name" does not contain a valid path.""", it
            )
        }

    private fun String.toPath(): Result<Path> = try {
        Result.success(Path(this))
    } catch (e: InvalidPathException) {
        Result.failure(e)
    }
}

internal object SystemContext : Context() {
    override fun getSystemProperty(name: String): String? = System.getProperty(name)
    override fun getEnvironmentVariable(name: String): String? = System.getenv(name)
}
