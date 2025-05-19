plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "ru.itis.clientserverapp.graph_screen"
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

    // AndroidX
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.material3)

    // Compose
    implementation(libs.coil.compose)

    // UI Components
    implementation(libs.material)
    implementation(libs.chart)

    // Lifecycle
    implementation(libs.lifecycle.viewmodel)

    // Coroutines
    implementation(libs.coroutines)

    // Network
    implementation(libs.bundles.network.deps)

    // Dependency Injection
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
}
