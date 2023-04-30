import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.core")
    id("dev.bogdanzurac.marp.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.lib.flagging.firebase"
}

dependencies {
    implementation(libs.firebase.config)

    implementation(project(projects.core))
}