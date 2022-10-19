import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"

}

allprojects {
    group = "pl.allegro.agh.cache"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(platform(kotlin("bom")))
        implementation(platform("org.springframework.boot:spring-boot-dependencies:2.5.5"))

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("io.micrometer:micrometer-registry-prometheus:1.8.0")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}


