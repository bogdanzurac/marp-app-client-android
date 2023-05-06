plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.dashboard.ui"
}

dependencies {
    api(project(projects.coreAuth))
}
