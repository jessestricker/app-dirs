package de.jessestricker.appdirs

import java.nio.file.Path
import kotlin.io.path.div

public class AppDirs internal constructor(
    appName: String,
    userDirs: UserDirs,
) {
    public constructor(name: String) : this(name, UserDirs(SystemContext))

    public val configDir: Path = userDirs.configDir / appName
    public val cacheDir: Path = userDirs.cacheDir / appName
}
