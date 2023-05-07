import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.app)
}

android {
    namespace = "dev.bogdanzurac.marp.app.elgoog"

    defaultConfig {
        applicationId = "dev.bogdanzurac.marp.app.elgoog"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(libs.marp.core.db)
    implementation(libs.marp.feature.auth.ui)
    implementation(libs.marp.feature.crypto.data)
    implementation(libs.marp.feature.crypto.domain)
    implementation(libs.marp.feature.crypto.ui)
    implementation(libs.marp.feature.dashboard.ui)
    implementation(libs.marp.feature.notes.data)
    implementation(libs.marp.feature.notes.domain)
    implementation(libs.marp.feature.notes.ui)
    implementation(libs.marp.lib.db.firebase)
    implementation(libs.marp.lib.flagging.firebase)
    implementation(libs.marp.lib.tracking.firebase)
    implementation(project(projects.featureMoviesData))
    implementation(project(projects.featureMoviesDomain))
    implementation(project(projects.featureMoviesUi))
    implementation(project(projects.featureNewsData))
    implementation(project(projects.featureNewsDataWebNewsData))
    implementation(project(projects.featureNewsDomain))
    implementation(project(projects.featureNewsUi))
    implementation(project(projects.featureWeatherData))
    implementation(project(projects.featureWeatherDomain))
    implementation(project(projects.featureWeatherUi))
    implementation(project(projects.libServicesGoogle))
    implementation(project(projects.libServicesHuawei))
}
