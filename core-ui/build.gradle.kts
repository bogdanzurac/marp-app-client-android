import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.compose")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.core.ui"
}

dependencies {
    implementation(libs.androidx.activity)
    implementation(libs.bundles.lifecycle)


    implementation(libs.marp.core)
    api(project(projects.corePrompts))
}