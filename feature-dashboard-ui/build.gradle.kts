plugins {
    alias(libs.plugins.marp.feature.ui)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.dashboard.ui"
}

dependencies {
    api(libs.marp.core.auth)
}
