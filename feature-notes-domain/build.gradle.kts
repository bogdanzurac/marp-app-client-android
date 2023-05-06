plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.domain")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.domain"
}

dependencies {
    implementation(project(parts.coreAuth))
    api(project(parts.featureCryptoDomain))
}
