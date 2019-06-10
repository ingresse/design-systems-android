// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    kotlin("jvm") version "1.3.11"
    `maven-publish`
    `build-scan`
    maven
}

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.4.1")
        classpath(kotlin("gradle-plugin", "1.3.11"))
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    publishAlways()
}

group = "com.ingresse"
version = "0.0.1"

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repository")
        }
        maven("${project.rootDir}/releases")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
