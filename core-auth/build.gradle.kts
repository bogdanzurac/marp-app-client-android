plugins {
    alias(libs.plugins.marp.core)
    alias(libs.plugins.marp.koin)
}

android {
    namespace = "dev.bogdanzurac.marp.core.auth"
}

dependencies {
    implementation(libs.firebase.auth)

    implementation(libs.marp.core)
    implementation(libs.marp.core.ws)
}