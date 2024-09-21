import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "2.0.0"
	kotlin("plugin.spring") version "2.0.0"
}

kotlin {
	jvmToolchain(21)
}

allprojects {
	group = "com.kopring.studylogs"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

// https://github.com/kotest/kotest/releases
val kotestVersion = "5.9.1"

// Apply the plugins to all submodules
subprojects {
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
		testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
		testImplementation ("io.kotest:kotest-property:$kotestVersion")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}

	tasks.withType<Test>().configureEach {
		useJUnitPlatform()
	}
}

tasks.named<BootJar>("bootJar") {
	enabled = false
}

tasks.named<Jar>("jar") {
	enabled = true
}
