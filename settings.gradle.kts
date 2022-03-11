pluginManagement {
    plugins {
//        kotlin("jvm") version "1.6.10"
        kotlin("kapt") version "1.6.10"
        kotlin("multiplatform") version "1.6.10"
        kotlin("plugin.serialization") version "1.6.10"
        id("org.jetbrains.kotlin.multiplatform") version "1.6.10"
        id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
        id("org.jlleitschuh.gradle.ktlint-idea") version "10.2.1"
        id("com.adarshr.test-logger") version "3.0.0"
        id("com.jfrog.bintray") version "1.8.5"
        id("maven-publish")
        id("jacoco")
        id("se.patrikerdes.use-latest-versions") version "0.2.15"
        id("com.github.ben-manes.versions") version "0.38.0"
        id("org.jetbrains.dokka") version "1.6.10"
        signing
    }

    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "sb-validation-dsl"
