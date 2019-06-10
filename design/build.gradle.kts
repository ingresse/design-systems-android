plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions.exclude("META-INF/main.kotlin_module")
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.0.2")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    implementation("androidx.core:core-ktx:1.0.2")

    api("com.google.android.material:material:1.0.0")

    implementation(kotlin("stdlib"))
    testImplementation("junit:junit:4.12")
}
