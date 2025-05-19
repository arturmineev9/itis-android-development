pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ClientServerAppBase"
include(":app")
include(":core:data")
include(":core:domain")
include(":feature:mainpage")
include(":core:base")
include(":feature:dog-details")
include(":core:navigation")
include(":core:network")
include(":core:base-feature")
include(":core:utils")
include(":feature:graph-screen")
