plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.domain")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.weather.domain"
}

dependencies {
    api(project(parts.coreServices))
}