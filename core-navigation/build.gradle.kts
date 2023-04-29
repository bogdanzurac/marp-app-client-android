import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
}

android {
    namespace = "dev.bogdanzurac.marp.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation)

    implementation(project(projects.core))
}