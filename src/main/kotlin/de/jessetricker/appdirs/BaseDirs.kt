package de.jessetricker.appdirs

import java.nio.file.Path
import kotlin.io.path.div

sealed interface BaseDirs : Dirs {
    companion object {
        val CURRENT: BaseDirs
            get() = when (OperatingSystem.CURRENT) {
                OperatingSystem.LINUX -> LinuxBaseDirs
                OperatingSystem.MAC_OS -> MacOSBaseDirs
                OperatingSystem.WINDOWS -> WindowsBaseDirs
            }
    }
}

internal data object LinuxBaseDirs : BaseDirs {
    override val userConfigDir: Path
        get() = system.getEnvVarAsPathOrNull("XDG_CONFIG_HOME")
            ?: (system.homeDir / ".config")

    override val userCacheDir: Path
        get() = system.getEnvVarAsPathOrNull("XDG_CACHE_HOME")
            ?: (system.homeDir / ".cache")
}

internal data object MacOSBaseDirs : BaseDirs {
    override val userConfigDir: Path get() = libraryDir / "Application Support"
    override val userCacheDir: Path get() = libraryDir / "Caches"

    private val libraryDir get() = system.homeDir / "Library"
}

internal data object WindowsBaseDirs : BaseDirs {
    override val userConfigDir: Path get() = system.getEnvVarAsPath("APPDATA")
    override val userCacheDir: Path get() = system.getEnvVarAsPath("LOCALAPPDATA")
}
