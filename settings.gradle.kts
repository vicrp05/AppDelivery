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
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // Permite repos locales si es necesario
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // Asegura que JitPack está presente
    }
}

rootProject.name = "app33"
include(":app")