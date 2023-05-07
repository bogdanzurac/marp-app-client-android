import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.data)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.news.data"
}

dependencies {
    api(project(projects.featureNewsDomain))
}
