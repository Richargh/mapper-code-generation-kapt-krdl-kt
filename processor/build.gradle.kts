plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    /** Language dependencies **/
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    /** Project dependencies **/
    implementation(project(":annotations"))

    /** Kapt dependencies **/
    implementation("com.google.auto.service:auto-service:1.0-rc6")
    kapt("com.google.auto.service:auto-service:1.0-rc6")
    implementation("com.squareup:kotlinpoet:0.7.0")

    /** Test dependencies **/
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.0")
    testImplementation("org.assertj:assertj-core:3.11.1")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.0")
}

