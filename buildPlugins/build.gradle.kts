plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.plugin)
    implementation(libs.crashlytics.plugin)
    implementation(libs.google.play.plugin)
    implementation(libs.kotlin.plugin)
    implementation(libs.ksp.plugin)
    implementation(libs.realm.plugin)
    implementation(libs.serialization.plugin)
}

gradlePlugin {
    plugins {
        register("appPlugin") {
            id = "dev.bogdanzurac.marp.plugins.app"
            implementationClass = "dev.bogdanzurac.marp.buildplugins.AppPlugin"
        }

        register("corePlugin") {
            id = "dev.bogdanzurac.marp.plugins.core"
            implementationClass = "dev.bogdanzurac.marp.buildplugins.CorePlugin"
        }

        register("koinPlugin") {
            id = "dev.bogdanzurac.marp.plugins.koin"
            implementationClass = "dev.bogdanzurac.marp.buildplugins.KoinPlugin"
        }

        register("featureDataPlugin") {
            id = "dev.bogdanzurac.marp.plugins.core.data"
            implementationClass = "dev.bogdanzurac.marp.buildplugins.FeatureDataPlugin"
        }

        register("featureDomainPlugin") {
            id = "dev.bogdanzurac.marp.plugins.core.domain"
            implementationClass = "dev.bogdanzurac.marp.buildplugins.FeatureDomainPlugin"
        }

        register("featurePresentationPlugin") {
            id = "dev.bogdanzurac.marp.plugins.core.presentation"
            implementationClass = "dev.bogdanzurac.marp.buildplugins.FeaturePresentationPlugin"
        }
    }
}
