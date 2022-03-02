plugins {
    id("kotlin")
    id("com.android.lint")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(Libs.AndroidTools.Lint.api)
    compileOnly(Libs.AndroidTools.Lint.checks)
    compileOnly(Libs.Kotlin.stdLib)
}

tasks.withType(Jar::class) {
    manifest {
        attributes("Lint-Registry-v2" to "com.admiral.lint.LintRegistry")
    }
}