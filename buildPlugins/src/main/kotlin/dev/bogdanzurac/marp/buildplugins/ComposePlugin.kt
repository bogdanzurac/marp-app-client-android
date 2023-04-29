package dev.bogdanzurac.marp.buildplugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ComposePlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        android {
            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion =
                    versionCatalog.findVersion("composeKotlinCompiler").value
            }
        }

        dependencies {
            debugImplementation(versionCatalog.lib("androidx-compose-ui-tooling"))
            implementation(versionCatalog.bundle("compose"))
            implementation(versionCatalog.lib("koin-compose"))
        }
    }
}