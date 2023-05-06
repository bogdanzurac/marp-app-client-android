plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.data")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.news.data.newsapi"
}

dependencies {
    implementation(project(parts.featureNewsData))
}
