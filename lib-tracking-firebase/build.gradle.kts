import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.core)
    alias(libs.plugins.marp.koin)
}

android {
    namespace = "dev.bogdanzurac.marp.lib.tracking.firebase"
}

dependencies {
    implementation(libs.firebase.analytics)

    implementation(libs.marp.core)
    implementation(libs.marp.core.ui)
}