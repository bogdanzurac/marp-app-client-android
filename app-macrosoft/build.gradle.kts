import dev.bogdanzurac.marp.build.projects

plugins {
    alias(libs.plugins.marp.app)
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
    implementation(libs.marp.core.db)
    implementation(libs.marp.feature.crypto.data)
    implementation(libs.marp.feature.crypto.domain)
    implementation(libs.marp.feature.crypto.ui)
    implementation(libs.marp.feature.dashboard.ui)
    implementation(libs.marp.lib.db.firebase)
    implementation(libs.marp.lib.flagging.firebase)
    implementation(libs.marp.lib.tracking.firebase)
    implementation(project(projects.featureNewsData))
    implementation(project(projects.featureNewsDataWebNewsApi))
    implementation(project(projects.featureNewsDomain))
    implementation(project(projects.featureNewsUi))
    implementation(project(projects.featureNotesDomain))
    implementation(project(projects.featureWeatherData))
    implementation(project(projects.featureWeatherDomain))
    implementation(project(projects.featureWeatherUi))
    implementation(project(projects.libServicesGoogle))
}
