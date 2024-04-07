package de.jessetricker.appdirs

import java.nio.file.Path

interface Dirs {
    val userConfigDir: Path
    val userCacheDir: Path
}
