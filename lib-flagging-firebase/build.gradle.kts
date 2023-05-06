plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.lib.flagging.firebase"
}

dependencies {
    implementation(libs.firebase.config)

    implementation(project(projects.core))
}