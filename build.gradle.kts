import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61" apply false
    kotlin("kapt") version "1.3.61" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

}

subprojects {

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            showExceptions = true
            showStandardStreams = true
            events("passed", "skipped", "failed")
        }
    }
}

tasks.wrapper {
    gradleVersion = "6.0.1"
}
