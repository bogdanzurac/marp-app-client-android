plugins {
    alias(libs.plugins.marp.feature.ui)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.auth.ui"
}

dependencies {
    implementation(libs.marp.core.auth)
}