package de.jessestricker.appdirs

import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.io.path.Path

class IntegrationTest : FunSpec({
    test("app dirs on Linux") {
        val context = MockContext(
            systemProperties = mapOf("os.name" to "Linux"),
            environmentVariables = mapOf(
                "XDG_CONFIG_HOME" to "/home/mock-user/xdgConfigHome",
                "XDG_CACHE_HOME" to "/home/mock-user/xdgCacheHome",
            )
        )

        val appDirs = AppDirs("mock-app", UserDirs(context))

        appDirs.configDir shouldBe Path("/home/mock-user/xdgConfigHome/mock-app")
        appDirs.cacheDir shouldBe Path("/home/mock-user/xdgCacheHome/mock-app")
    }

    test("app dirs on Linux using fallback") {
        val context = MockContext(
            systemProperties = mapOf("os.name" to "Linux"),
            environmentVariables = mapOf("HOME" to "/home/mock-user")
        )

        val appDirs = AppDirs("mock-app", UserDirs(context))

        appDirs.configDir shouldBe Path("/home/mock-user/.config/mock-app")
        appDirs.cacheDir shouldBe Path("/home/mock-user/.cache/mock-app")
    }

    test("app dirs on macOS") {
        val context = MockContext(
            systemProperties = mapOf(
                "os.name" to "Mac OS X",
                "user.home" to "/Users/Mock.User",
            )
        )

        val appDirs = AppDirs("mock-app", UserDirs(context))

        appDirs.configDir shouldBe Path("/Users/Mock.User/Library/Application Support/mock-app")
        appDirs.cacheDir shouldBe Path("/Users/Mock.User/Library/Caches/mock-app")
    }

    test("app dirs on Windows 11") {
        val context = MockContext(
            systemProperties = mapOf("os.name" to "Windows 11"),
            environmentVariables = mapOf(
                "APPDATA" to "C:/Users/MockUser/AppData/Roaming",
                "LOCALAPPDATA" to "C:/Users/MockUser/AppData/Local",
            )
        )

        val appDirs = AppDirs("mock-app", UserDirs(context))

        appDirs.configDir shouldBe Path("C:/Users/MockUser/AppData/Roaming/mock-app")
        appDirs.cacheDir shouldBe Path("C:/Users/MockUser/AppData/Local/mock-app")
    }

    test("unsupported operating system") {
        val context = MockContext(
            systemProperties = mapOf("os.name" to "Mocked OS"),
        )

        shouldThrowWithMessage<UnsupportedOperatingSystemError>(
            """The current operating system "Mocked OS" is unsupported."""
        ) {
            AppDirs("mock-app", UserDirs(context))
        }
    }
})
