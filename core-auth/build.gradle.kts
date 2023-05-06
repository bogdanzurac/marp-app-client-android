import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.core.auth"
}

dependencies {
    implementation(libs.firebase.auth)

    implementation(libs.marp.core)
    implementation(project(projects.coreWs))
}