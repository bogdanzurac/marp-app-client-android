import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
    id("dev.bogdanzurac.marp.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.lib.services.huawei"
}

dependencies {
    implementation(libs.huawei.location)

    implementation(project(projects.core))
    implementation(project(projects.corePrompts))
    implementation(project(projects.coreServices))
}