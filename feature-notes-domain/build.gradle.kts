plugins {
    alias(libs.plugins.marp.feature.domain)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.domain"
}

dependencies {
    implementation(libs.marp.core.auth)
    api(libs.marp.feature.crypto.domain)
}
