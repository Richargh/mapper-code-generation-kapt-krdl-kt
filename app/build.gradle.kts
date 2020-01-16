plugins {
    kotlin("jvm")
    kotlin("kapt")

    application
}

application {
    mainClassName = "de.richargh.sandbox.kaptcodegen.app.mainKt"
}

dependencies {
    /** Language dependencies **/
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    /** Project dependencies **/
    implementation(project(":annotations"))
    implementation(project(":processor"))
    kapt(project(":processor"))

    /** Main dependencies **/
    // none
}