import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.core)
    alias(libs.plugins.marp.koin)
}

android {
    namespace = "dev.bogdanzurac.marp.lib.services.google"
}

dependencies {
    implementation(libs.google.play.location)
    implementation(libs.kotlin.play.services)

    implementation(libs.marp.core)
    implementation(libs.marp.core.prompts)
    implementation(project(projects.coreServices))
}