import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.feature.data")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.news.data"
}

dependencies {
    api(project(projects.featureNewsDomain))
}
