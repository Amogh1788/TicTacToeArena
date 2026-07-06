plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.amogh"
version = "1.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(26))
    }
}
javafx {
    version = "24"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
        "javafx.media"
    )
}

application {
    mainClass.set("app.Main")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}