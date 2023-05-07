import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.core)
}

android {
    namespace = "dev.bogdanzurac.marp.core.services"
}

dependencies {
    implementation(libs.marp.core)
    implementation(project(projects.coreUi))
}