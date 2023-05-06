plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.lib.services.huawei"
}

dependencies {
    implementation(libs.huawei.location)

    implementation(project(parts.core))
    implementation(project(parts.corePrompts))
    implementation(project(parts.coreServices))
}