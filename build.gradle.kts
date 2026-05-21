import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.compose") version "1.6.11"
}

group = "kr.gracelove.qrfiler"
version = "1.0.1"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.google.zxing:core:3.5.3")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "qrfiler"
            packageVersion = "1.0.1"

            macOS {
                iconFile.set(file("src/main/resources/qrfiler.icns"))
            }
        }
    }
}

// Post-build: sign the .app bundle for Gatekeeper
afterEvaluate {
    tasks.findByName("createDistributable")?.let { t ->
        t.doLast {
            val app = file("build/compose/binaries/main/app/qrfiler.app")
            if (!app.exists()) return@doLast
            exec {
                commandLine("bash", file("scripts/sign-app.sh").absolutePath, app.absolutePath)
            }
        }
    }
}
