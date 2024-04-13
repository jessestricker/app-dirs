package de.jessestricker.appdirs

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SystemContextTest : FunSpec({
    for (name in System.getProperties().stringPropertyNames().asSequence().shuffled().take(10)) {
        test("returns system property '$name'") {
            SystemContext.getSystemProperty(name) shouldBe System.getProperty(name)
        }
    }

    for (name in System.getenv().keys.asSequence().shuffled().take(10)) {
        test("returns environment variable '$name'") {
            SystemContext.getEnvironmentVariable(name) shouldBe System.getenv(name)
        }
    }
})
