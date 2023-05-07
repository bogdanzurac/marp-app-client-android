import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.data)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.data"
}

dependencies {
    implementation(libs.marp.core.auth)
    implementation(libs.marp.lib.db.firebase)
    implementation(project(projects.featureNotesDomain))
}