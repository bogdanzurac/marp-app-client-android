import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.ui)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.news.ui"
}

dependencies {
    api(project(projects.featureNewsDomain))
}
