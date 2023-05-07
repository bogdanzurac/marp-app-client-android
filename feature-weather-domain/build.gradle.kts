import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.domain)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.weather.domain"
}

dependencies {
    api(project(projects.coreServices))
}