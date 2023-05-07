import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.core)
    alias(libs.plugins.marp.compose)
    alias(libs.plugins.marp.koin)
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