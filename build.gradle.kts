plugins {
    alias(libs.plugins.kotlin)
    `java-library`
    `maven-publish`

    alias(libs.plugins.detekt)
    alias(libs.plugins.kover)
}

group = "de.jessestricker"
version = "0.1.0-pre-0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.assertions.core)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/jessestricker/app-dirs")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

detekt {
    allRules = false
    basePath = rootProject.projectDir.absolutePath
}
