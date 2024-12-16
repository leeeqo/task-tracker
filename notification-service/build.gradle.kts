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

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-devtools")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")

    // Mail
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // Logger
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")

    // Kotlin necessities
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
