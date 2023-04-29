import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
}

android {
    namespace = "dev.bogdanzurac.marp.core.ws"
}

dependencies {
    implementation(libs.bundles.ktor)
    api(libs.ktor.core)

    implementation(project(projects.core))
}