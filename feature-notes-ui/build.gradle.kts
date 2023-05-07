import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.ui)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.ui"
}

dependencies {
    implementation(libs.marp.core.auth)
    implementation(project(projects.featureCryptoUiCommon))
    implementation(project(projects.featureNotesDomain))
    implementation(project(projects.featureNotesUiCommon))
}
