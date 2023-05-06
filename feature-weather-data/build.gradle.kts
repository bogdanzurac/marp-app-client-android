plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.data")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.weather.data"
}

dependencies {
    implementation(project(parts.featureWeatherDomain))
}