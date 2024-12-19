import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"

    id("org.springframework.boot") version "3.2.6"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "com.leeeqo"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
}

ext {
    set("springCloudVersion", "2022.0.3")
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway:3.1.2")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.2")
    implementation("org.springframework.boot:spring-boot-devtools")

    // Kotlin necessities
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Logger
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")

    runtimeOnly("org.springframework.boot:spring-boot-starter-tomcat")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}
