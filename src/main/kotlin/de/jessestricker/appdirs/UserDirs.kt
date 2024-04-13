package de.jessestricker.appdirs

import java.nio.file.Path
import kotlin.io.path.div

internal sealed interface UserDirs {
    val configDir: Path
    val cacheDir: Path
}

internal class UnsupportedOperatingSystemError(name: String) :
    Error("""The current operating system "$name" is unsupported.""")

internal fun UserDirs(context: Context): UserDirs {
    val osName = context.getStandardSystemProperty("os.name")
    return when {
        osName == "Linux" -> LinuxUserDirs(context)
        osName == "Mac OS X" -> MacOsUserDirs(context)
        osName.startsWith("Windows") -> WindowsUserDirs(context)
        else -> throw UnsupportedOperatingSystemError(osName)
    }
}

internal class LinuxUserDirs(context: Context) : UserDirs {
    override val configDir: Path = context.getEnvironmentVariablePath("XDG_CONFIG_HOME")
        ?: (context.getStandardEnvironmentVariablePath("HOME") / ".config")

    override val cacheDir: Path = context.getEnvironmentVariablePath("XDG_CACHE_HOME")
        ?: (context.getStandardEnvironmentVariablePath("HOME") / ".cache")
}

internal class MacOsUserDirs(context: Context) : UserDirs {
    override val configDir: Path = context.libraryDir / "Application Support"
    override val cacheDir: Path = context.libraryDir / "Caches"

    private val Context.libraryDir get() = getStandardSystemPropertyPath("user.home") / "Library"
}

internal class WindowsUserDirs(context: Context) : UserDirs {
    override val configDir: Path = context.getStandardEnvironmentVariablePath("APPDATA")
    override val cacheDir: Path = context.getStandardEnvironmentVariablePath("LOCALAPPDATA")
}
