import com.github.jengelman.gradle.plugins.shadow.ShadowJavaPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.shadow) apply false
}

subprojects {
    group = "mondicoders"
    version = "0.0.1"

    plugins.withType<JavaPlugin> {
        configure<JavaPluginExtension> {
            toolchain {
                languageVersion = JavaLanguageVersion.of(17)
            }
        }

        tasks.named<Jar>("jar") {
            archiveClassifier = "just"
        }

        tasks.named<Test>("test") {
            useJUnitPlatform()
        }
    }

    // Technically, Ktor pulls this too, but reconfigures...
    plugins.withType<ShadowJavaPlugin> {
        tasks.register<Sync>("release") {
            destinationDir = rootDir.resolve("artifacts/")
            preserve { include("*") }
            from(tasks.named("shadowJar"))
        }
        tasks.named<ShadowJar>("shadowJar") {
            mergeServiceFiles()

            archiveClassifier = null
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            allWarningsAsErrors = true
        }
    }
}