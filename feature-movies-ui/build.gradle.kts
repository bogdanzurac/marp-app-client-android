plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.ui")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.movies.ui"
}

dependencies {
    api(project(parts.featureMoviesDomain))
}
