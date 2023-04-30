import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.dashboard.ui"
}

dependencies {
    api(project(projects.coreAuth))
}
