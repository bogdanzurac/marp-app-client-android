import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
}

android {
    namespace = "dev.bogdanzurac.marp.core.services"
}

dependencies {
    implementation(project(projects.core))
    implementation(project(projects.coreUi))
}