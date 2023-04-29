import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
    id("dev.bogdanzurac.marp.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.core.auth"
}

dependencies {
    implementation(libs.firebase.auth)

    implementation(project(projects.core))
    implementation(project(projects.coreWs))
}