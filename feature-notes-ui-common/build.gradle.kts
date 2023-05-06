import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.ui.common"
}

dependencies {
    implementation(project(projects.featureNotesDomain))
}