import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
}

android {
    namespace = "dev.bogdanzurac.marp.core.services"
}

dependencies {
    implementation(project(projects.core))
    implementation(project(projects.coreUi))
}