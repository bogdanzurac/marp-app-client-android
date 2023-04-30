import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.feature.data")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.data"
}

dependencies {
    implementation(project(projects.coreAuth))
    implementation(project(projects.featureNotesDomain))
    implementation(project(projects.libDbFirebase))
}