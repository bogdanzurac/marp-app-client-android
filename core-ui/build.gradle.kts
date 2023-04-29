import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
    id("dev.bogdanzurac.marp.plugins.compose")
    id("dev.bogdanzurac.marp.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.core.ui"
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.bundles.lifecycle)


    implementation(project(projects.core))
    api(project(projects.corePrompts))
}