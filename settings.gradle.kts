plugins {
    `gradle-enterprise`
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.4.0")
}


rootProject.name = "homework_web"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {}
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":backend",
    ":frontend"
)

project(":backend").projectDir = file("src/backend")
project(":frontend").projectDir = file("src/frontend")


gradleEnterprise.buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}