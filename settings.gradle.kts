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

rootProject.name = "WordsPerMinute"
include(":app")
include(":domain-data-typespeed")
include(":feature-component-keystroke-tracking-textfield")
include(":feature-component-wordsperminute")
include(":feature-game-setup-screen")
include(":feature-game-screen")
include(":foundation-coroutines")
include(":foundation-strings")
include(":foundation-ui")
