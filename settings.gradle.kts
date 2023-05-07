import java.net.URI

include(":app-elgoog")
include(":app-macrosoft")
include(":core-services")
include(":feature-auth-ui")
include(":feature-crypto-data")
include(":feature-crypto-domain")
include(":feature-crypto-ui")
include(":feature-crypto-ui-common")
include(":feature-dashboard-ui")
include(":feature-movies-data")
include(":feature-movies-domain")
include(":feature-movies-ui")
include(":feature-news-data")
include(":feature-news-data-newsapi")
include(":feature-news-data-newsdata")
include(":feature-news-domain")
include(":feature-news-ui")
include(":feature-notes-data")
include(":feature-notes-domain")
include(":feature-notes-ui")
include(":feature-notes-ui-common")
include(":feature-weather-data")
include(":feature-weather-domain")
include(":feature-weather-ui")
include(":lib-db-firebase")
include(":lib-flagging-firebase")
include(":lib-services-google")
include(":lib-services-huawei")
include(":lib-tracking-firebase")

pluginManagement {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = java.net.URI("https://maven.pkg.github.com/bogdanzurac/marp-android-packages")
            name = "GitHub"
            credentials {
                val properties = java.util.Properties()
                properties.load(file("project.properties").inputStream())
                username = properties["github.username"].toString()
                password = properties["github.passwordStep1"].toString() +
                        properties["github.passwordStep2"].toString()
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    versionCatalogs {
        create("libs") {
            from("dev.bogdanzurac.marp.build:libs:0.0.7")
        }
    }

    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://developer.huawei.com/repo/") }
        maven {
            url = URI("https://maven.pkg.github.com/bogdanzurac/marp-android-packages")
            name = "GitHub"
            credentials {
                val properties = java.util.Properties()
                properties.load(file("project.properties").inputStream())
                username = properties["github.username"].toString()
                password = properties["github.passwordStep1"].toString() +
                        properties["github.passwordStep2"].toString()
            }
        }
    }
}
