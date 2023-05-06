import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.lib.tracking.firebase"
}

dependencies {
    implementation(libs.firebase.analytics)

    implementation(libs.marp.core)
    implementation(project(projects.coreUi))
}