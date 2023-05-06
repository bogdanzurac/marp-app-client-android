

import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.compose")
}

android {
    namespace = "dev.bogdanzurac.marp.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation)

    implementation(project(projects.core))
}