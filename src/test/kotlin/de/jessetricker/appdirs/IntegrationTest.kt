package de.jessetricker.appdirs

import de.jessetricker.appdirs.util.withFakeSystem
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS.LINUX
import org.junit.jupiter.api.condition.OS.MAC
import org.junit.jupiter.api.condition.OS.WINDOWS
import kotlin.io.path.Path

class IntegrationTest {
    @Nested
    @EnabledOnOs(LINUX)
    inner class Linux {
        @Test
        fun `Linux uses XDG environment variables`() {
            withFakeSystem(
                properties = mapOf("os.name" to "Linux"),
                environmentVariables = mapOf(
                    "XDG_CONFIG_HOME" to "/home/user/.xdgConfigDir",
                    "XDG_CACHE_HOME" to "/home/user/.xdgCacheDir",
                )
            ) {
                val actualAppDirs = AppDirs("appName")

                actualAppDirs.userConfigDir shouldBeEqual Path("/home/user/.xdgConfigDir/appName")
                actualAppDirs.userCacheDir shouldBeEqual Path("/home/user/.xdgCacheDir/appName")
            }
        }

        @Test
        fun `Linux falls back to HOME environment variable`() {
            withFakeSystem(
                properties = mapOf("os.name" to "Linux"),
                environmentVariables = mapOf("HOME" to "/home/user")
            ) {
                val actualAppDirs = AppDirs("appName")

                actualAppDirs.userConfigDir shouldBeEqual Path("/home/user/.config/appName")
                actualAppDirs.userCacheDir shouldBeEqual Path("/home/user/.cache/appName")
            }
        }
    }

    @Nested
    @EnabledOnOs(MAC)
    inner class MacOS {
        @Test
        fun `macOS uses HOME environment variables`() {
            withFakeSystem(
                properties = mapOf("os.name" to "Mac OS X"),
                environmentVariables = mapOf("HOME" to "/Users/user")
            ) {
                val actualAppDirs = AppDirs("appName")

                actualAppDirs.userConfigDir shouldBeEqual Path("/Users/user/Library/Application Support/appName")
                actualAppDirs.userCacheDir shouldBeEqual Path("/Users/user/Library/Caches/appName")
            }
        }
    }

    @Nested
    @EnabledOnOs(WINDOWS)
    inner class Windows {
        @Test
        fun `Windows uses APPDATA environment variables`() {
            withFakeSystem(
                properties = mapOf("os.name" to "Windows 11"),
                environmentVariables = mapOf(
                    "APPDATA" to """C:\Users\User\AppData\Roaming""",
                    "LOCALAPPDATA" to """C:\Users\User\AppData\Local""",
                )
            ) {
                val actualAppDirs = AppDirs("appName")

                actualAppDirs.userConfigDir shouldBeEqual Path("""C:\Users\User\AppData\Roaming\appName""")
                actualAppDirs.userCacheDir shouldBeEqual Path("""C:\Users\User\AppData\Local\appName""")
            }
        }
    }
}
