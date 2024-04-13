package de.jessetricker.appdirs

import de.jessetricker.appdirs.util.withFakeSystem
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class OperatingSystemTest : FunSpec({
    test("current operating system throws when system property is unset") {
        withFakeSystem {
            shouldThrow<InternalError> {
                OperatingSystem.CURRENT
            }
        }
    }

    test("current operating system throws when unsupported") {
        withFakeSystem(properties = mapOf("os.name" to "Unsupported OS")) {
            shouldThrow<UnsupportedOperatingSystemError> {
                OperatingSystem.CURRENT
            }
        }
    }

    test("current operating system is Linux") {
        withFakeSystem(properties = mapOf("os.name" to "Linux")) {
            OperatingSystem.CURRENT shouldBe OperatingSystem.LINUX
        }
    }

    test("current operating system is macOS") {
        withFakeSystem(properties = mapOf("os.name" to "Mac OS X")) {
            OperatingSystem.CURRENT shouldBe OperatingSystem.MAC_OS
        }
    }

    test("current operating system is Windows") {
        withFakeSystem(properties = mapOf("os.name" to "Windows 11")) {
            OperatingSystem.CURRENT shouldBe OperatingSystem.WINDOWS
        }
    }
})
