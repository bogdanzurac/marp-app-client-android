plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.crypto.ui.common"
}

dependencies {
    implementation(project(parts.featureCryptoDomain))
}