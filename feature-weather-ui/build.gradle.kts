import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.ui)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.weather.ui"
}

dependencies {
    implementation(project(projects.featureWeatherDomain))
}