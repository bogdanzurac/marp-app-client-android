import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.ui)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.ui.common"
}

dependencies {
    implementation(project(projects.featureNotesDomain))
}