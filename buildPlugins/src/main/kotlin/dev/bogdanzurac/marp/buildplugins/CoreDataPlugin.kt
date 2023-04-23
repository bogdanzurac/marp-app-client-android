package dev.bogdanzurac.marp.buildplugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CoreDataPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
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
                compose = false
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
            implementation(versionCatalog.lib("kermit"))
            implementation(versionCatalog.bundle("koin"))
            ksp(versionCatalog.lib("koin-compiler"))
            implementation(versionCatalog.lib("kotlin-coroutines"))
            implementation(versionCatalog.lib("kotlin-datetime"))
            implementation(versionCatalog.lib("kotlin-serialization"))
            implementation(versionCatalog.bundle("ktor"))
            implementation(versionCatalog.lib("multiplatform-settings"))
            implementation(versionCatalog.lib("realm"))
            implementation(versionCatalog.lib("store"))
        }
    }
}