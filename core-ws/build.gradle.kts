plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
}

android {
    namespace = "dev.bogdanzurac.marp.core.ws"
}

dependencies {
    implementation(libs.bundles.ktor)
    api(libs.ktor.core)

    implementation(project(projects.core))
}