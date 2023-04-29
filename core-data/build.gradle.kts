import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
    id("dev.bogdanzurac.marp.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.core.data"
}

dependencies {
    api(libs.multiplatform.settings)
    implementation(libs.store)

    implementation(project(projects.core))
}