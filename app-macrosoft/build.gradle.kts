plugins {
    id("dev.bogdanzurac.marp.build.plugins.app")
}

android {
    namespace = "dev.bogdanzurac.marp.app.macrosoft"

    defaultConfig {
        applicationId = "dev.bogdanzurac.marp.app.macrosoft"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(parts.coreDb))
    implementation(project(parts.coreNavigation))
    implementation(project(parts.featureCryptoData))
    implementation(project(parts.featureCryptoDomain))
    implementation(project(parts.featureCryptoUi))
    implementation(project(parts.featureDashboardUi))
    implementation(project(parts.featureNewsData))
    implementation(project(parts.featureNewsDataWebNewsApi))
    implementation(project(parts.featureNewsDomain))
    implementation(project(parts.featureNewsUi))
    implementation(project(parts.featureNotesDomain))
    implementation(project(parts.featureWeatherData))
    implementation(project(parts.featureWeatherDomain))
    implementation(project(parts.featureWeatherUi))
    implementation(project(parts.libDbFirebase))
    implementation(project(parts.libFlaggingFirebase))
    implementation(project(parts.libServicesGoogle))
    implementation(project(parts.libTrackingFirebase))
}
