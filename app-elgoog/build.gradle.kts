import dev.bogdanzurac.marp.buildplugins.project
import dev.bogdanzurac.marp.buildplugins.projects

plugins {
    id("dev.bogdanzurac.marp.plugins.app")
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
    implementation(libs.androidx.activity)
    implementation(libs.androidx.core)
    implementation(libs.firebase.crashlytics)
    implementation(libs.coil)
    implementation(libs.kotlin.datetime)
    implementation(libs.kotlin.serialization)
    implementation(libs.ktor.core)

    implementation(project(projects.coreAuth))
    implementation(project(projects.coreData))
    implementation(project(projects.coreDb))
    implementation(project(projects.coreNavigation))
    implementation(project(projects.coreServices))
    implementation(project(projects.coreUi))
    implementation(project(projects.coreWs))
    implementation(project(projects.libDbFirebase))
    implementation(project(projects.libFlaggingFirebase))
    implementation(project(projects.libServicesGoogle))
    implementation(project(projects.libServicesHuawei))
    implementation(project(projects.libTrackingFirebase))
}
