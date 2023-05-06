plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.data")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.data"
}

dependencies {
    implementation(project(parts.coreAuth))
    implementation(project(parts.featureNotesDomain))
    implementation(project(parts.libDbFirebase))
}