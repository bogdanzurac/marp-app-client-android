plugins {
    alias(libs.plugins.marp.core)
    alias(libs.plugins.marp.koin)
}

android {
    namespace = "dev.bogdanzurac.marp.lib.db.firebase"
}

dependencies {
    api(libs.firebase.firestore)

    implementation(libs.marp.core)
}