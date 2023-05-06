plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.auth.ui"
}

dependencies {
    implementation(project(projects.coreAuth))
}