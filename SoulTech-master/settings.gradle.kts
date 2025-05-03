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
        maven {
            url = uri("https://maven.pkg.github.com/Yermakhan05/chatLibrary")
            credentials {
                username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("USERNAME_GITHUB") ?: ""
                password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("TOKEN_GITHUB") ?: ""
            }
        }
    }
}

rootProject.name = "My Faith"
include(":app")
include(":domain")
include(":data")
