plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
}

android {
    namespace = "dev.bogdanzurac.marp.core.services"
}

dependencies {
    implementation(project(parts.core))
    implementation(project(parts.coreUi))
}