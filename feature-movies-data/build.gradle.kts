plugins {
    id("dev.bogdanzurac.marp.build.plugins.feature.data")
}

android {
    namespace = "dev.bogdanzurac.marp.feature.movies.data"
}

dependencies {
    api(project(parts.featureMoviesDomain))
}
