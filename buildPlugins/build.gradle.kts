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

        register("coreDataPlugin") {
            id = "dev.bogdanzurac.marp.plugins.core.data"
            implementationClass = "dev.bogdanzurac.marp.buildplugins.CoreDataPlugin"
        }

        register("coreDomainPlugin") {
            id = "dev.bogdanzurac.marp.plugins.core.domain"
            implementationClass = "dev.bogdanzurac.marp.buildplugins.CoreDomainPlugin"
        }

        register("corePresentationPlugin") {
            id = "dev.bogdanzurac.marp.plugins.core.presentation"
            implementationClass = "dev.bogdanzurac.marp.buildplugins.CorePresentationPlugin"
        }
    }
}
