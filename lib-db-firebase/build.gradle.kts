import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.core")
    id("dev.bogdanzurac.marp.build.plugins.koin")
}

android {
    namespace = "dev.bogdanzurac.marp.lib.db.firebase"
}

dependencies {
    api(libs.firebase.firestore)

    implementation(project(projects.core))
}