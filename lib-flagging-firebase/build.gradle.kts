plugins {
    alias(libs.plugins.marp.core)
    alias(libs.plugins.marp.koin)
}

android {
    namespace = "dev.bogdanzurac.marp.lib.flagging.firebase"
}

dependencies {
    implementation(libs.firebase.config)

    implementation(libs.marp.core)
}