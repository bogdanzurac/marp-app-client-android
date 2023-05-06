plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.core.auth"
}

dependencies {
    implementation(libs.firebase.auth)

    implementation(project(parts.core))
    implementation(project(parts.coreWs))
}