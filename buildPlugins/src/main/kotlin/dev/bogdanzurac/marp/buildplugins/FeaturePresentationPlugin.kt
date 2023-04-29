package dev.bogdanzurac.marp.buildplugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeaturePresentationPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
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
                compose = true
                aidl = false
                renderScript = false
                shaders = false
            }

            composeOptions {
                kotlinCompilerExtensionVersion =
                    versionCatalog.findVersion("composeKotlinCompiler").value
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
            debugImplementation(versionCatalog.lib("androidx-compose-ui-tooling"))
            implementation(versionCatalog.lib("androidx-core"))
            implementation(versionCatalog.bundle("androidx-lifecycle"))
            implementation(versionCatalog.lib("androidx-navigation"))
            implementation(versionCatalog.lib("coil"))
            implementation(versionCatalog.bundle("compose"))
            implementation(versionCatalog.lib("kermit"))
            implementation(versionCatalog.lib("koin-compose"))
            implementation(versionCatalog.bundle("kotlin-coroutines"))
            implementation(versionCatalog.bundle("kotlin-datetime"))

            implementation(project("core"))
        }
    }
}