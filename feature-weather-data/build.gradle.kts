import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.feature.data")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.weather.data"
}

dependencies {
    implementation(project(projects.featureWeatherDomain))
}