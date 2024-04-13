package de.jessestricker.appdirs

import java.nio.file.Path
import kotlin.io.path.div

class AppDirs internal constructor(
    appName: String,
    userDirs: UserDirs,
) {
    constructor(name: String) : this(name, UserDirs(SystemContext))

    val configDir: Path = userDirs.configDir / appName
    val cacheDir: Path = userDirs.cacheDir / appName
}
