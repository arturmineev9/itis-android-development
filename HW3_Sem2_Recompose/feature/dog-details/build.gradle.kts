plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "ru.itis.clientserverapp.dog_details"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        viewBinding = true
        compose = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core Modules
    implementation(project(path = ":core:base-feature"))
    implementation(project(path = ":core:domain"))
    implementation(project(path = ":core:utils"))

    // AndroidX
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.material3)

    // Compose
    implementation(libs.coil.compose)

    // UI
    implementation(libs.material)
    implementation(libs.glide)

    // Lifecycle
    implementation(libs.lifecycle.viewmodel)

    // Coroutines
    implementation(libs.coroutines)

    // Dependency Injection
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
}
