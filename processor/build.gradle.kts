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
//    implementation("com.squareup:kotlinpoet:0.7.0")
}

