plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "ru.itis.clientserverapp.app"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.itis.clientserverapp"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = rootProject.extra.get("versionCode") as Int
        versionName = rootProject.extra.get("versionName") as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    dependencies {
        // Core Modules
        implementation(project(path = ":core:base"))
        implementation(project(path = ":core:base-feature"))
        implementation(project(path = ":core:data"))
        implementation(project(path = ":core:domain"))
        implementation(project(path = ":core:navigation"))
        implementation(project(path = ":core:network"))

        // Feature Modules
        implementation(project(path = ":feature:dog-details"))
        implementation(project(path = ":feature:mainpage"))

        // AndroidX
        implementation(libs.androidx.activity)
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.androidx.core.ktx)

        // UI
        implementation(libs.material)

        // Navigation
        implementation(libs.navigation.fragment)
        implementation(libs.navigation.ui)

        // Dependency Injection
        implementation(libs.hilt)
        ksp(libs.hilt.compiler)
    }
}
