pluginManagement {
    repositories {
        google()
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
rootProject.name = "Movies"
include(":app")
include(":data")
include(":domain")
include(":common-ui")
include(":feature-details")
include(":feature-fav")
include(":feature-main")
include(":common-test")
