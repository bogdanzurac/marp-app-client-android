plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.news.ui"
}

dependencies {
    api(project(parts.featureNewsDomain))
}
