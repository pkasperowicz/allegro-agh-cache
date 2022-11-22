
plugins {
	id("org.springframework.boot") version "2.7.4"
	kotlin("jvm")
	kotlin("plugin.spring")
}

group = "pl.allegro.agh.cache.app"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.micrometer:micrometer-registry-prometheus")
	implementation("com.github.ben-manes.caffeine:caffeine")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
