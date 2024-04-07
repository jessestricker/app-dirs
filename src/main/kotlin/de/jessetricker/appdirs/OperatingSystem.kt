package de.jessetricker.appdirs

class UnsupportedOperatingSystemError(name: String) : Error("""The current operating system "$name" is unsupported.""")

enum class OperatingSystem(private val namePrefix: String) {
    LINUX("Linux"),
    MAC_OS("Mac OS X"),
    WINDOWS("Windows");

    companion object {
        val CURRENT: OperatingSystem
            get() {
                val name = system.osName
                val current = entries.find { name.startsWith(it.namePrefix) }
                return current ?: throw UnsupportedOperatingSystemError(name)
            }
    }
}
