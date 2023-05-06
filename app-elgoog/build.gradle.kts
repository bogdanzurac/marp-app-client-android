plugins {
    id("dev.bogdanzurac.marp.build.plugins.app")
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
    implementation(project(parts.coreDb))
    implementation(project(parts.coreNavigation))
    implementation(project(parts.featureAuthUi))
    implementation(project(parts.featureCryptoData))
    implementation(project(parts.featureCryptoDomain))
    implementation(project(parts.featureCryptoUi))
    implementation(project(parts.featureDashboardUi))
    implementation(project(parts.featureMoviesData))
    implementation(project(parts.featureMoviesDomain))
    implementation(project(parts.featureMoviesUi))
    implementation(project(parts.featureNewsData))
    implementation(project(parts.featureNewsDataWebNewsData))
    implementation(project(parts.featureNewsDomain))
    implementation(project(parts.featureNewsUi))
    implementation(project(parts.featureNotesData))
    implementation(project(parts.featureNotesDomain))
    implementation(project(parts.featureNotesUi))
    implementation(project(parts.featureWeatherData))
    implementation(project(parts.featureWeatherDomain))
    implementation(project(parts.featureWeatherUi))
    implementation(project(parts.libDbFirebase))
    implementation(project(parts.libFlaggingFirebase))
    implementation(project(parts.libServicesGoogle))
    implementation(project(parts.libServicesHuawei))
    implementation(project(parts.libTrackingFirebase))
}
