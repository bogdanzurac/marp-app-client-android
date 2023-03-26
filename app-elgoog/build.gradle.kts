plugins {
    id("com.android.application") version "7.4.2"
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("com.google.firebase.crashlytics") version "2.9.4"
    id("com.google.gms.google-services") version "4.3.15"
    id("io.realm.kotlin") version "1.6.0"
    id("org.jetbrains.kotlin.android") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
}

android {
    namespace = "dev.bogdanzurac.marp.app.elgoog"
    compileSdk = 33

    defaultConfig {
        applicationId = "dev.bogdanzurac.marp.app.elgoog"
        buildToolsVersion = "33.0.2"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    androidComponents.onVariants { variant ->
        val name = variant.name
        sourceSets {
            getByName(name).kotlin.srcDir("${buildDir.absolutePath}/generated/ksp/${name}/kotlin")
        }
    }
}

val composeVersion = "1.3.3"
val koinKspVersion = "1.1.1"
val ktorVersion = "2.2.3"
val navVersion = "2.5.3"

dependencies {
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")
    implementation("androidx.compose.material3:material3:1.0.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("co.touchlab:kermit:1.2.2")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.firebase:firebase-analytics-ktx:21.2.0")
    implementation("com.google.firebase:firebase-config-ktx:21.2.1")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.3.6")
    implementation("com.huawei.hms:location:6.4.0.300")
    implementation("com.russhwolf:multiplatform-settings:1.0.0")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.insert-koin:koin-android:3.3.3")
    implementation("io.insert-koin:koin-androidx-compose:3.4.2")
    implementation("io.insert-koin:koin-annotations:$koinKspVersion")
    ksp("io.insert-koin:koin-ksp-compiler:$koinKspVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.realm.kotlin:library-base:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.mobilenativefoundation.store:store5:5.0.0-alpha04")
}
