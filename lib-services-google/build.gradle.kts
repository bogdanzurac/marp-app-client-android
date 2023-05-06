plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.lib.services.google"
}

dependencies {
    implementation(libs.google.play.location)
    implementation(libs.kotlin.play.services)

    implementation(project(projects.core))
    implementation(project(projects.corePrompts))
    implementation(project(projects.coreServices))
}