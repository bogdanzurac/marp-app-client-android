import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.domain)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.notes.domain"
}

dependencies {
    implementation(libs.marp.core.auth)
    api(project(projects.featureCryptoDomain))
}
