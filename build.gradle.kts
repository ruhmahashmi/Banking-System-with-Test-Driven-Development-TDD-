plugins {
    id("java") // Core Java plugin
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral() // For JUnit 5
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") // Test engine
}

tasks.test {
    useJUnitPlatform() // JUnit 5
    testLogging {
        events("passed", "skipped", "failed") // Log results
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17 // JDK 17
    targetCompatibility = JavaVersion.VERSION_17
}