val kotlinVersion: String by project
val tabulateVersion: String by project

plugins {
    java
    kotlin("jvm")
    id("org.jetbrains.dokka") version "1.4.32"
}

group = "io.github.voytech"
version = "0.1.0-SNAPSHOT"


repositories {
    mavenCentral()
    maven {
       url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(platform("io.github.voytech:tabulate-bom:$tabulateVersion"))
    implementation("io.github.voytech","tabulate-core")
    implementation("io.github.voytech","tabulate-excel")
    implementation("io.github.voytech","tabulate-csv")
    implementation("io.projectreactor:reactor-core:3.4.6")
}

