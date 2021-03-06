plugins {
    kotlin("jvm")
    kotlin("kapt")

    application
}

application {
    mainClassName = "de.richargh.sandbox.kaptcodegen.app.MainKt"
}

dependencies {
    /** Language dependencies **/
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    /** Project dependencies **/
    implementation(project(":annotations"))
    compileOnly(project(":processor"))
    kapt(project(":processor"))

    /** Main dependencies **/
    // none

    /** Test dependencies **/
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.0")
    testImplementation("org.assertj:assertj-core:3.11.1")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.0")
}