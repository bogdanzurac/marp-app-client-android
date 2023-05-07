import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.core)
    alias(libs.plugins.marp.koin)
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