import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.sun.jersey:jersey-core:1.19.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.netflix.conductor:conductor-client:3.13.6")
    implementation("com.netflix.conductor:conductor-common:3.13.6")
    implementation("com.auth0:auth0:2.3.0")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.6") // to hit endpoints easily using spring-boot
    // fixes an issue where 2 different packages had different versions of this one
    implementation("com.sun.jersey:jersey-core:1.19.4")
    implementation("com.sun.jersey:jersey-client:1.19.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
