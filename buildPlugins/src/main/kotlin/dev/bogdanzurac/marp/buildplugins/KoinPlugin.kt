package dev.bogdanzurac.marp.buildplugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {

        with(pluginManager) {
            apply("com.google.devtools.ksp")
        }

        dependencies {
            implementation(versionCatalog.bundle("koin"))
            ksp(versionCatalog.lib("koin-compiler"))
        }
    }
}