import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.android) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.google.play) apply false
    alias(libs.plugins.realm) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.marp.app) apply false
    alias(libs.plugins.marp.koin) apply false
}