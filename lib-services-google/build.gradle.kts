import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
    id("dev.bogdanzurac.marp.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.lib.services.google"
}

dependencies {
    implementation(libs.google.play.location)
    implementation(libs.kotlin.play.services)

    implementation(project(projects.core))
    implementation(project(projects.corePrompts))
    implementation(project(projects.coreServices))
}