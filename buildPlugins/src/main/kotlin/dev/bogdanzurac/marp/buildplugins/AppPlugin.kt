package dev.bogdanzurac.marp.buildplugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies

class AppPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }

        configureKotlin(JavaVersion.VERSION_11)

        android {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
                apply("io.realm.kotlin")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            compileSdk = versionCatalog.findVersion("androidCompileSdk").intValue
            buildToolsVersion = versionCatalog.findVersion("androidBuildTools").value

            defaultConfig {
                minSdk = versionCatalog.findVersion("androidMinSdk").intValue
            }

            buildFeatures {
                buildConfig = true
                compose = true
                aidl = false
                renderScript = false
                shaders = false
            }

            composeOptions {
                kotlinCompilerExtensionVersion =
                    versionCatalog.findVersion("composeKotlinCompiler").value
            }

            packagingOptions {
                resources {
                    excludes.add("/META-INF/{AL2.0,LGPL2.1}")
                }
            }

            buildTypes {
                release {
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            androidComponents.onVariants { variant ->
                val name = variant.name
                sourceSets {
                    getByName(name).kotlin.srcDir("${buildDir.absolutePath}/generated/ksp/${name}/kotlin")
                }
            }
        }

        dependencies {
            debugImplementation(versionCatalog.lib("androidx-compose-ui-tooling"))
            implementation(versionCatalog.lib("androidx-navigation"))
            implementation(versionCatalog.bundle("compose"))
            implementation(versionCatalog.bundle("lifecycle"))
            implementation(versionCatalog.lib("kermit"))
            implementation(versionCatalog.bundle("koin"))
            ksp(versionCatalog.lib("koin-compiler"))
            implementation(versionCatalog.lib("kotlin-coroutines"))
        }
    }

    private fun Project.android(configure: Action<ApplicationExtension>): Unit =
        extensions.configure("android", configure)

    private val Project.androidComponents: ApplicationAndroidComponentsExtension
        get() = (this as ExtensionAware).extensions.getByName("androidComponents")
                as ApplicationAndroidComponentsExtension
}