import dev.bogdanzurac.marp.build.projects

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
    implementation(project(projects.coreDb))
    implementation(project(projects.coreNavigation))
    implementation(project(projects.featureCryptoData))
    implementation(project(projects.featureCryptoDomain))
    implementation(project(projects.featureCryptoUi))
    implementation(project(projects.featureDashboardUi))
    implementation(project(projects.featureNewsData))
    implementation(project(projects.featureNewsDataWebNewsApi))
    implementation(project(projects.featureNewsDomain))
    implementation(project(projects.featureNewsUi))
    implementation(project(projects.featureNotesDomain))
    implementation(project(projects.featureWeatherData))
    implementation(project(projects.featureWeatherDomain))
    implementation(project(projects.featureWeatherUi))
    implementation(project(projects.libDbFirebase))
    implementation(project(projects.libFlaggingFirebase))
    implementation(project(projects.libServicesGoogle))
    implementation(project(projects.libTrackingFirebase))
}
