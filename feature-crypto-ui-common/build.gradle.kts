import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.crypto.ui.common"
}

dependencies {
    implementation(project(projects.featureCryptoDomain))
}