plugins {
    alias(libs.plugins.marp.core)
    alias(libs.plugins.marp.compose)
}

android {
    namespace = "dev.bogdanzurac.marp.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation)

    implementation(libs.marp.core)
}