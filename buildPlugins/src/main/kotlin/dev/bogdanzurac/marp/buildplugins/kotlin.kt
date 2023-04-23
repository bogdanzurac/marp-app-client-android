package dev.bogdanzurac.marp.buildplugins

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.configureKotlin(javaVersion: JavaVersion) {
    android {
        // Set JVM targets to the same version, for consistency
        compileOptions {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }

        kotlinOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting `kotlin.warningsAsErrors = true` in gradle.properties
            allWarningsAsErrors =
                project.properties["kotlin.warningsAsErrors"]?.toString().toBoolean()

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
            )

            // Set JVM targets to the same version, for consistency
            jvmTarget = javaVersion.toString()
        }
    }

    // Set JVM targets to the same version, for consistency
    java {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}

private fun Project.android(block: Action<CommonExtension<*, *, *, *>>): Unit =
    extensions.configure("android", block)


private fun Project.java(block: Action<JavaPluginExtension>): Unit =
    extensions.configure("java", block)

private fun CommonExtension<*, *, *, *>.kotlinOptions(block: Action<KotlinJvmOptions>) =
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
