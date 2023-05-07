import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.data)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.news.data.newsapi"
}

dependencies {
    implementation(project(projects.featureNewsData))
}
