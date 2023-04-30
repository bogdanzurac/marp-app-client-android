import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.feature.domain")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.weather.domain"
}

dependencies {
    api(project(projects.coreServices))
}