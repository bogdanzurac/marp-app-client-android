import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.data)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.crypto.data"
}

dependencies {
    implementation(project(projects.featureCryptoDomain))
}