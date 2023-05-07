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
    implementation(project(projects.featureAuthUi))
    implementation(project(projects.featureCryptoData))
    implementation(project(projects.featureCryptoDomain))
    implementation(project(projects.featureCryptoUi))
    implementation(project(projects.featureDashboardUi))
    implementation(project(projects.featureMoviesData))
    implementation(project(projects.featureMoviesDomain))
    implementation(project(projects.featureMoviesUi))
    implementation(project(projects.featureNewsData))
    implementation(project(projects.featureNewsDataWebNewsData))
    implementation(project(projects.featureNewsDomain))
    implementation(project(projects.featureNewsUi))
    implementation(project(projects.featureNotesData))
    implementation(project(projects.featureNotesDomain))
    implementation(project(projects.featureNotesUi))
    implementation(project(projects.featureWeatherData))
    implementation(project(projects.featureWeatherDomain))
    implementation(project(projects.featureWeatherUi))
    implementation(project(projects.libDbFirebase))
    implementation(project(projects.libFlaggingFirebase))
    implementation(project(projects.libServicesGoogle))
    implementation(project(projects.libServicesHuawei))
    implementation(project(projects.libTrackingFirebase))
}
