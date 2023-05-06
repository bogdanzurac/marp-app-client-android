plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.crypto.ui"
}

dependencies {
    implementation(project(parts.coreAuth))
    implementation(project(parts.featureCryptoDomain))
    implementation(project(parts.featureCryptoUiCommon))
    implementation(project(parts.featureNotesDomain))
    implementation(project(parts.featureNotesUiCommon))
}
