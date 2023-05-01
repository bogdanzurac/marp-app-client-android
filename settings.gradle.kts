import java.net.URI

include(":app-elgoog")
include(":core")
include(":core-auth")
include(":core-data")
include(":core-db")
include(":core-navigation")
include(":core-prompts")
include(":core-services")
include(":core-ui")
include(":core-ws")
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
    includeBuild("buildPlugins")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }

    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://developer.huawei.com/repo/") }
    }
}
