import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.feature.ui)
}

android {
    namespace = "dev.bogdanzurac.marp.feature.movies.ui"
}

dependencies {
    api(project(projects.featureMoviesDomain))
}
