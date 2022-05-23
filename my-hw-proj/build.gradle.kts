import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("plugin.jpa") version "1.4.32"
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
	kotlin("plugin.allopen") version "1.4.32"
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

group = "org"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.getByName<Test>("test") {
	useJUnitPlatform()
	testLogging {
		outputs.upToDateWhen { false }
	}
	addTestListener(object : TestListener {
		override fun beforeTest(p0: TestDescriptor?) = Unit
		override fun beforeSuite(p0: TestDescriptor?) = Unit
		override fun afterTest(desc: TestDescriptor, result: TestResult) = Unit
		override fun afterSuite(desc: TestDescriptor, result: TestResult) {
			printResults(desc, result)
		}
	})
}

fun printResults(desc: TestDescriptor, result: TestResult) {
	if (desc.parent == null) {
		val output = result.run {
			"Results: $resultType (" +
					"$testCount tests, " +
					"$successfulTestCount successes, " +
					"$failedTestCount failures, " +
					"$skippedTestCount skipped" +
					")"
		}
		val testResultLine = "|  $output  |"
		val repeatLength = testResultLine.length
		val seperationLine = "-".repeat(repeatLength)
		println(seperationLine)
		println(testResultLine)
		println(seperationLine)
	}
}
