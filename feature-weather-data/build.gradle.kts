import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.data)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.weather.data"
}

dependencies {
    implementation(project(projects.featureWeatherDomain))
}