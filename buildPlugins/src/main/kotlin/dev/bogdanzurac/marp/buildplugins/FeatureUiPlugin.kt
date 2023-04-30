package dev.bogdanzurac.marp.buildplugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureUiPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("dev.bogdanzurac.marp.plugins.compose")
            apply("dev.bogdanzurac.marp.plugins.koin")
        }

        configureKotlin(JavaVersion.VERSION_11)

        android {
            compileSdk = versionCatalog.findVersion("androidCompileSdk").intValue
            buildToolsVersion = versionCatalog.findVersion("androidBuildTools").value

            defaultConfig {
                minSdk = versionCatalog.findVersion("androidMinSdk").intValue
            }

            buildFeatures {
                buildConfig = false
                aidl = false
                renderScript = false
                shaders = false
            }

            buildTypes {
                release {
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
        }

        dependencies {
            implementation(versionCatalog.lib("androidx-activity"))
            implementation(versionCatalog.lib("androidx-core"))
            implementation(versionCatalog.lib("androidx-lifecycle"))
            implementation(versionCatalog.lib("androidx-navigation"))
            implementation(versionCatalog.lib("coil"))
            implementation(versionCatalog.lib("kermit"))
            implementation(versionCatalog.lib("kotlin-coroutines"))
            implementation(versionCatalog.lib("kotlin-datetime"))

            implementation(project("core"))
            implementation(project("core-navigation"))
            implementation(project("core-ui"))
        }
    }
}