package de.jessetricker.appdirs

import java.nio.file.Path
import kotlin.io.path.div

class AppDirs(private val appName: String) : Dirs {
    override val userConfigDir: Path get() = BaseDirs.CURRENT.userConfigDir / appName
    override val userCacheDir: Path get() = BaseDirs.CURRENT.userCacheDir / appName
}
