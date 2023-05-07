import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.data)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.data"
}

dependencies {
    implementation(libs.marp.core.auth)
    implementation(project(projects.featureNotesDomain))
    implementation(project(projects.libDbFirebase))
}