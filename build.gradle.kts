plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"

    id("org.beryx.jlink") version "3.1.3"
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
jlink {

    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))

    launcher {
        name = "TicTacToe Arena"
    }

    forceMerge("javafx")

    jpackage {
        imageName = "TicTacToe Arena"
        installerType = "app-image"
        appVersion = "1.1.0"
        vendor = "AG Studios"
        icon = "src/main/resources/icons/tictactoe_arena_icon.ico"
    }
}