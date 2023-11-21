buildscript {
    repositories {
        mavenCentral()
    }
}
plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}
