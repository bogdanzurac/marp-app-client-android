plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.ui"
}

dependencies {
    implementation(project(projects.coreAuth))
    implementation(project(projects.featureCryptoUiCommon))
    implementation(project(projects.featureNotesDomain))
    implementation(project(projects.featureNotesUiCommon))
}
