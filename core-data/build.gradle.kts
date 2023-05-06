plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.core.data"
}

dependencies {
    api(libs.multiplatform.settings)
    implementation(libs.store)

    implementation(project(projects.core))
}