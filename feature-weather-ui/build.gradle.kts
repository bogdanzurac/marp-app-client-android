import dev.bogdanzurac.marp.build.projects

plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.weather.ui"
}

dependencies {
    implementation(project(projects.featureWeatherDomain))
}