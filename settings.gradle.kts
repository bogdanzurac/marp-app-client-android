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
