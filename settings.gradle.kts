pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement { repositories { mavenCentral() } }

rootProject.name = "kotlin-emailx"

include("emailx", "example")
