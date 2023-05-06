import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.lib.services.huawei"
}

dependencies {
    implementation(libs.huawei.location)

    implementation(libs.marp.core)
    implementation(project(projects.corePrompts))
    implementation(project(projects.coreServices))
}